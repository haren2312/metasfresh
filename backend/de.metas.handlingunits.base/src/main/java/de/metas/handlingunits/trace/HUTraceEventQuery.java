package de.metas.handlingunits.trace;

import com.google.common.collect.ImmutableSet;
import de.metas.document.DocTypeId;
import de.metas.handlingunits.HuId;
import de.metas.inout.InOutId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inventory.InventoryId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.With;
import org.adempiere.mmovement.MovementId;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;
import java.util.OptionalInt;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Used to pass to {@link HUTraceRepository#query(HUTraceEventQuery)} to retrieve {@link HUTraceEvent}s.
 * <p>
 * This class has the properties that {@link HUTraceEvent} has, but the following differences:
 * <ul>
 * <li>none of those properties is mandatory, all may be {@code null}
 * <li>there is the mandatory {@link RecursionMode}
 * </ul>
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Value
@Builder
@With
public class HUTraceEventQuery
{
	public enum RecursionMode
	{
		BACKWARD, FORWARD, BOTH, NONE
	}

	@NonNull
	@Default
	RecursionMode recursionMode = RecursionMode.NONE;

	public enum EventTimeOperator
	{
		/**
		 * only expects {@link #eventTime} to be set.
		 */
		EQUAL,

		/**
		 * Expects both {@link #eventTime} and {@link #eventTimeTo} to be set.
		 */
		BETWEEN
	}

	@Default
	EventTimeOperator eventTimeOperator = EventTimeOperator.EQUAL;

	Instant eventTime;

	Instant eventTimeTo;

	@NonNull
	@Default
	OptionalInt huTraceEventId = OptionalInt.empty();

	OrgId orgId;

	@NonNull
	@Singular
	ImmutableSet<HUTraceType> types;

	@NonNull
	@Singular
	ImmutableSet<HuId> vhuIds;

	ProductId productId;

	@Nullable
	Quantity qty;

	String vhuStatus;

	@Singular
	@NonNull
	ImmutableSet<HuId> topLevelHuIds;

	HuId vhuSourceId;

	InOutId inOutId;

	ShipmentScheduleId shipmentScheduleId;

	MovementId movementId;

	@Nullable
	InventoryId inventoryId;

	PPCostCollectorId ppCostCollectorId;

	PPOrderId ppOrderId;

	String docStatus;

	@NonNull
	@Default
	Optional<DocTypeId> docTypeId = Optional.empty();

	int huTrxLineId;

	// Lotnumber is currently not set from webui DocumentFilterParams. When it was added, it wasn't intended to be (not 100% sure whether it should have been).
	@Nullable
	String lotNumber;

	/**
	 * Might be a HU-trace-record's VHU, source-VHU or TopLevel-HU.
	 */
	@Nullable
	HuId anyHuId;
}
