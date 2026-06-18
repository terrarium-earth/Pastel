package earth.terrarium.pastel.compat.REI.widgets;

import me.shedaniel.math.Point;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.impl.client.gui.widget.EntryWidget;

import java.util.function.Supplier;

public class IndexedEntryWidget extends EntryWidget {

    private final Supplier<Integer> indexer;

    public IndexedEntryWidget(Point point, Supplier<Integer> indexer) {
        super(point);
        this.indexer = indexer;
    }

    @Override
    public EntryStack<?> getCurrentEntry() {
        return getCyclingEntries()
            .get()
            .get(indexer.get());
    }
}
