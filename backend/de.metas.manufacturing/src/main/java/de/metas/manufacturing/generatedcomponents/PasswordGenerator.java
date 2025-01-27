/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.manufacturing.generatedcomponents;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Borrowed from: https://mkyong.com/java/java-password-generator-example/
 */
public class PasswordGenerator implements IComponentGenerator
{
	@SuppressWarnings("SpellCheckingInspection")
	private static final String CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
	private static final String CHAR_UPPERCASE = CHAR_LOWERCASE.toUpperCase();
	private static final String DIGIT = "0123456789";
	private static final String PUNCTUATION = "+.,?!()=";

	@VisibleForTesting
	static final String PARAM_TOTAL_LENGTH = "totalLength";
	@VisibleForTesting
	static final String PARAM_USE_LOWERCASE = "useLowercase";
	@VisibleForTesting
	static final String PARAM_USE_UPPERCASE = "useUppercase";
	@VisibleForTesting
	static final String PARAM_USE_DIGIT = "useDigit";
	@VisibleForTesting
	static final String PARAM_USE_PUNCTUATION = "usePunctuation";
	@VisibleForTesting
	static final String PARAM_GROUP_SEPARATOR = "groupSeparator";
	@VisibleForTesting
	static final String PARAM_GROUP_SIZE = "groupSize";

	private static final ComponentGeneratorParams DEFAULT_PARAMETERS = ComponentGeneratorParams.builder()
			.parameter(PARAM_TOTAL_LENGTH, "14")
			.parameter(PARAM_USE_LOWERCASE, StringUtils.ofBoolean(true))
			.parameter(PARAM_USE_UPPERCASE, StringUtils.ofBoolean(true))
			.parameter(PARAM_USE_DIGIT, StringUtils.ofBoolean(true))
			.parameter(PARAM_USE_PUNCTUATION, StringUtils.ofBoolean(true))
			.parameter(PARAM_GROUP_SEPARATOR, "-")
			.parameter(PARAM_GROUP_SIZE, "4")
			.build();

	private static final String NO_PASSWORD = null;

	@VisibleForTesting
	static final ImmutableList<AttributeCode> supportedAttributes = ImmutableList.of(
			AttributeConstants.RouterPassword,
			AttributeConstants.RouterPassword1,
			AttributeConstants.RouterPassword2,
			AttributeConstants.RouterPassword3,
			AttributeConstants.RouterPassword4,
			AttributeConstants.RouterPassword5,
			AttributeConstants.RouterPassword6,
			AttributeConstants.RouterPassword7,
			AttributeConstants.RouterPassword8
	);

	private final Random random = new Random();

	@Override
	public ImmutableAttributeSet generate(@NonNull final ComponentGeneratorContext context)
	{
		final int qty = context.getQty();
		Check.errorIf(qty < 1 || qty > supportedAttributes.size(),
				"Qty of Password attributes should be between 1 and {} but it was {}", supportedAttributes.size(), qty);

		//
		// Count how many attributes were already generated.
		// Identify which are the attributes which are not already generated (the free slots).
		int countAlreadyGenerated = 0;
		final ArrayList<AttributeCode> attributesAvailableToGenerate = new ArrayList<>();
		final ImmutableAttributeSet existingAttributes = context.getExistingAttributes();
		for (final AttributeCode attributeCode : supportedAttributes)
		{
			if (!existingAttributes.hasAttribute(attributeCode))
			{
				continue;
			}

			final boolean alreadyGenerated = !context.isOverrideExistingValues()
					&& Check.isNotBlank(existingAttributes.getValueAsString(attributeCode));
			if (alreadyGenerated)
			{
				countAlreadyGenerated++;
			}
			else
			{
				attributesAvailableToGenerate.add(attributeCode);
			}
		}

		//
		// Compute how much we still have to generate.
		final int countRemainingToGenerate = qty - countAlreadyGenerated;
		if (countRemainingToGenerate <= 0)
		{
			return ImmutableAttributeSet.EMPTY;
		}
		final int availableSize = attributesAvailableToGenerate.size();
		if (countRemainingToGenerate > availableSize)
		{
			throw new AdempiereException("We still have to generate " + countRemainingToGenerate + " but only " + attributesAvailableToGenerate + " are available and not already generated");
		}

		//
		// Generate the remaining ones
		final ImmutableAttributeSet.Builder result = ImmutableAttributeSet.builder();
		for (int i = 0; i < countRemainingToGenerate; i++)
		{
			final AttributeCode attributeCode = attributesAvailableToGenerate.get(i);
			final String password = generatePassword(context);

			result.attributeValue(attributeCode, password);
		}

		for (int i = countRemainingToGenerate; i < availableSize; i++)
		{
			final AttributeCode attributeCode = attributesAvailableToGenerate.get(i);
			result.attributeValue(attributeCode, NO_PASSWORD);
		}

		return result.build();
	}

	@Override
	public ComponentGeneratorParams getDefaultParameters()
	{
		return DEFAULT_PARAMETERS;
	}

	private String generatePassword(@NonNull final ComponentGeneratorContext context)
	{
		final ComponentGeneratorParams parameters = context.getParameters();

		return generatePassword(
				StringUtils.toIntegerOrZero(parameters.getValue(PARAM_TOTAL_LENGTH)),
				StringUtils.toBoolean(parameters.getValue(PARAM_USE_LOWERCASE)),
				StringUtils.toBoolean(parameters.getValue(PARAM_USE_UPPERCASE)),
				StringUtils.toBoolean(parameters.getValue(PARAM_USE_DIGIT)),
				StringUtils.toBoolean(parameters.getValue(PARAM_USE_PUNCTUATION)),
				parameters.getValue(PARAM_GROUP_SEPARATOR),
				StringUtils.toIntegerOrZero(parameters.getValue(PARAM_GROUP_SIZE)));
	}

	@NonNull
	@VisibleForTesting
	String generatePassword(
			final int totalLength,
			final boolean useLowercase,
			final boolean useUppercase,
			final boolean useDigit,
			final boolean usePunctuation,
			final String groupSeparator,
			final int groupSize
	)
	{
		if ((totalLength < 1))
		{
			throw new AdempiereException("Password length must be > 0");
		}

		String FILL_CHARACTERS = "";
		final StringBuilder result = new StringBuilder(totalLength);

		if (useLowercase)
		{
			// guaranteed 2 chars (lowercase)
			result.append(generateRandomString(CHAR_LOWERCASE, 2));

			FILL_CHARACTERS += CHAR_LOWERCASE;
		}

		if (useUppercase)
		{
			// guaranteed 2 chars (uppercase)
			result.append(generateRandomString(CHAR_UPPERCASE, 2));

			FILL_CHARACTERS += CHAR_UPPERCASE;
		}

		if (useDigit)
		{
			// guaranteed 2 digits
			result.append(generateRandomString(DIGIT, 2));

			FILL_CHARACTERS += DIGIT;
		}

		if (usePunctuation)
		{
			// guaranteed 2 punctuation
			result.append(generateRandomString(PUNCTUATION, 2));

			// don't fill with punctuation
			// FILL_CHARACTERS += PUNCTUATION;
		}

		{
			// remaining until length: random
			result.append(generateRandomString(FILL_CHARACTERS, totalLength - result.length()));
		}

		final String shuffledString = shuffleString(result.toString());

		return StringUtils.insertSeparatorEveryNCharacters(shuffledString, groupSeparator, groupSize).substring(0, totalLength);
	}

	@NonNull
	private String generateRandomString(@NonNull final String input, final int size)
	{
		if (size < 1)
		{
			return "";
		}

		final StringBuilder result = new StringBuilder(size);
		for (int i = 0; i < size; i++)
		{
			// produce a random order
			final int index = random.nextInt(input.length());
			result.append(input.charAt(index));
		}
		return result.toString();
	}

	@NonNull
	private static String shuffleString(@NonNull final String input)
	{
		final List<String> result = Arrays.asList(input.split(""));
		Collections.shuffle(result);

		return String.join("", result);
	}
}
