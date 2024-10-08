/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.currency;

import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
public class CurrencyRate
{
	@NonNull CurrencyId fromCurrencyId;
	@NonNull CurrencyPrecision fromCurrencyPrecision;

	@NonNull CurrencyId toCurrencyId;
	@NonNull CurrencyPrecision toCurrencyPrecision;

	@NonNull BigDecimal conversionRate;
	@NonNull Instant conversionDate;
	@NonNull CurrencyConversionTypeId conversionTypeId;

	public BigDecimal toBigDecimal() {return getConversionRate();}

	@NonNull
	public BigDecimal convertAmount(final BigDecimal amount)
	{
		return convertAmount(amount, toCurrencyPrecision);
	}

	@NonNull
	public BigDecimal convertAmount(@NonNull final BigDecimal amount, @NonNull final CurrencyPrecision precisionOverride)
	{
		final BigDecimal rate = getConversionRate();
		final BigDecimal amountConv = rate.multiply(amount);
		return precisionOverride.roundIfNeeded(amountConv);
	}

	/**
	 * Convert Money from "fromCurrency" to "toCurrency".
	 * <p>
	 * Example:
	 * <pre>
	 * this.fromCurrency = EUR
	 * this.toCurrency = CHF
	 * this.conversionRate = X
	 *
	 * amount = Money in EUR
	 * </pre>
	 * This method converts the amount from EUR to CHF using the conversionRate.
	 *
	 * @param amount amount in {@link #fromCurrencyId}
	 */
	public Money convertAmount(@NonNull final Money amount)
	{
		if (CurrencyId.equals(amount.getCurrencyId(), toCurrencyId))
		{
			return amount;
		}

		if (!CurrencyId.equals(amount.getCurrencyId(), fromCurrencyId))
		{
			throw new AdempiereException("Cannot convert " + amount + " to " + toCurrencyId + " using " + this);
		}

		final BigDecimal convertedAmount = convertAmount(amount.toBigDecimal());
		return Money.of(convertedAmount, toCurrencyId);
	}

	public Money convertAmount(@NonNull final Money amount, @NonNull final CurrencyId toCurrencyId)
	{
		if (!CurrencyId.equals(this.toCurrencyId, toCurrencyId))
		{
			throw new AdempiereException("Cannot convert " + amount + " to " + toCurrencyId + " using " + this);
		}

		return convertAmount(amount);
	}

	/**
	 * Convert Money from "toCurrency" to "fromCurrency" (ie. do the reverse conversion).
	 * <p>
	 * Example:
	 * <pre>
	 * this.fromCurrency = EUR
	 * this.toCurrency = CHF
	 * this.conversionRate = X
	 *
	 * amount = Money in CHF
	 * </pre>
	 * This method converts the amount from CHF to EUR using the conversionRate of EUR->CHF. The precision and rounding are those of EUR, for correctness.
	 *
	 * @param amount amount in {@link #toCurrencyId}
	 */
	public Money reverseConvertAmount(@NonNull final Money amount)
	{
		if (CurrencyId.equals(amount.getCurrencyId(), fromCurrencyId))
		{
			return amount;
		}

		if (!CurrencyId.equals(amount.getCurrencyId(), toCurrencyId))
		{
			throw new AdempiereException("Cannot convert " + amount + " to " + fromCurrencyId + " using " + this);
		}

		final BigDecimal convertedAmount = amount.toBigDecimal().divide(conversionRate, fromCurrencyPrecision.toInt(), fromCurrencyPrecision.getRoundingMode());
		return Money.of(convertedAmount, fromCurrencyId);
	}
}
