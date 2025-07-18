package earth.terrarium.pastel.api.energy.storage;

import earth.terrarium.pastel.api.energy.color.InkColor;

public class FixedSingleInkStorage extends SingleInkStorage {

    public FixedSingleInkStorage(long maxEnergy, InkColor color) {
        super(maxEnergy);
        this.storedColor = color;
    }

    public FixedSingleInkStorage(long maxEnergy, InkColor color, long amount) {
        super(maxEnergy, color, amount);
    }

    @Override
    public boolean accepts(InkColor color) {
        return this.storedColor == color;
    }

    @Override
    public long getRoom(InkColor color) {
        if (this.storedColor == color) {
            return this.maxEnergy - this.storedEnergy;
        } else {
            return 0;
        }
    }

}
