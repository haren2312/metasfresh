package de.metas.resource.qrcode;

import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.product.ResourceId;
import de.metas.resource.ManufacturingResourceType;
import de.metas.resource.Resource;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
@Builder
@Jacksonized // NOTE: we are making it json friendly mainly for snapshot testing
public class ResourceQRCode
{
	@NonNull ResourceId resourceId;
	@Nullable ManufacturingResourceType resourceType;
	@NonNull String caption;

	public static boolean equals(@Nullable final ResourceQRCode o1, @Nullable final ResourceQRCode o2)
	{
		return Objects.equals(o1, o2);
	}

	public String toGlobalQRCodeJsonString() {return ResourceQRCodeJsonConverter.toGlobalQRCodeJsonString(this);}

	public static ResourceQRCode ofGlobalQRCode(final GlobalQRCode globalQRCode) {return ResourceQRCodeJsonConverter.fromGlobalQRCode(globalQRCode);}

	public static ResourceQRCode ofGlobalQRCodeJsonString(final String qrCodeString)
	{
		return ResourceQRCodeJsonConverter.fromGlobalQRCodeJsonString(qrCodeString);
	}

	public static ResourceQRCode ofResource(@NonNull final Resource resource)
	{
		return ResourceQRCode.builder()
				.resourceId(resource.getResourceId())
				.resourceType(resource.getManufacturingResourceType())
				.caption(resource.getName().getDefaultValue())
				.build();
	}

	public PrintableQRCode toPrintableQRCode()
	{
		return PrintableQRCode.builder()
				.qrCode(toGlobalQRCodeJsonString())
				.bottomText(caption)
				.build();
	}

	public static boolean isTypeMatching(@NonNull final GlobalQRCode globalQRCode)
	{
		return ResourceQRCodeJsonConverter.isTypeMatching(globalQRCode);
	}
}
