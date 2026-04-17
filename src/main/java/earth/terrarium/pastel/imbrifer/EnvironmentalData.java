package earth.terrarium.pastel.imbrifer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record EnvironmentalData(float darkening, float brightMult, float fogNear, float fogFar) {

    public static final EnvironmentalData NOOP = new EnvironmentalData(0F, 1F, 1F, 1F);
    public static final Codec<EnvironmentalData> CODEC = RecordCodecBuilder.create(i -> i.group(
                                                                                             Codec.FLOAT.fieldOf(
                                                                                                 "darkening")
                                                                                                        .forGetter(EnvironmentalData::darkening),
                                                                                             Codec.FLOAT.fieldOf(
                                                                                                 "bright_mult")
                                                                                                        .forGetter(EnvironmentalData::brightMult),
                                                                                             Codec.FLOAT.fieldOf("near")
                                                                                                        .forGetter(EnvironmentalData::fogNear),
                                                                                             Codec.FLOAT.fieldOf("far")
                                                                                                        .forGetter(EnvironmentalData::fogFar)
                                                                                         )
                                                                                         .apply(
                                                                                             i,
                                                                                             EnvironmentalData::new
                                                                                         ));

    public EnvironmentalData(float fogFar, float fogNear) {
        this(NOOP.darkening, NOOP.brightMult, fogFar, fogNear);
    }

    public static EnvironmentalData fromArray(float[] data) {
        return new EnvironmentalData(data[0], data[1], data[2], data[3]);
    }

    public float[] asArray() {
        return new float[]{
            darkening, brightMult, fogNear, fogFar
        };
    }
}
