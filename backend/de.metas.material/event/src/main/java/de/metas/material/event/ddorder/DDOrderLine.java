package de.metas.material.event.ddorder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import de.metas.bpartner.BPartnerId;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.planning.ddorder.DistributionNetworkAndLineId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

/*
 * #%L
 * metasfresh-mrp
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
@Value
@Builder
@Jacksonized
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class DDOrderLine
{
	int salesOrderLineId;

	@NonNull ProductDescriptor productDescriptor;
	@Nullable MinMaxDescriptor fromWarehouseMinMaxDescriptor;
	@NonNull BigDecimal qtyMoved;
	@NonNull BigDecimal qtyToMove;

	@NonNull Instant demandDate;
	@Nullable DistributionNetworkAndLineId distributionNetworkAndLineId;
	int ddOrderLineId;
	@Nullable BPartnerId bpartnerId;

}
