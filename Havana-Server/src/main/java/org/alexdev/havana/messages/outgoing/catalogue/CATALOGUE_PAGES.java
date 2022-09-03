package org.alexdev.havana.messages.outgoing.catalogue;

import org.alexdev.havana.game.catalogue.CatalogueManager;
import org.alexdev.havana.game.catalogue.CataloguePage;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.List;

public class CATALOGUE_PAGES extends MessageComposer {
    private final int rank;
    private final boolean hasClub;
    private final List<CataloguePage> parentTabs;

    public CATALOGUE_PAGES(int rank, boolean hasClub, List<CataloguePage> parentTabs) {
        this.rank = rank;
        this.hasClub = hasClub;
        this.parentTabs = parentTabs;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(0); // Navigatable
        response.writeInt(0); // Colour
        response.writeInt(0); // Icon
        response.writeInt(-1); // Page id
        response.writeString("");
        response.writeInt(0);

        response.writeInt(this.parentTabs.size());

        for (CataloguePage childTab : this.parentTabs) {
            appendIndexNode(childTab, response);
            recursiveIndexNode(childTab, response);
        }
    }

    public void appendIndexNode(CataloguePage cataloguePage, NettyResponse response) {
        response.writeBool(true); // Navigatable
        response.writeInt(cataloguePage.getColour()); // Colour
        response.writeInt(cataloguePage.getIcon()); // Icon
        response.writeInt(cataloguePage.getId()); // Page id
        response.writeString(cataloguePage.getName());
        response.writeInt(0);
    }

    private void recursiveIndexNode(CataloguePage parentTab, NettyResponse response) {
        List<CataloguePage> childTabs = CatalogueManager.getInstance().getChildPages(parentTab.getId(), this.rank, this.hasClub);
        response.writeInt(childTabs.size());

        for (CataloguePage childTab : childTabs) {
            appendIndexNode(childTab, response);
            recursiveIndexNode(childTab, response);
        }
    }

    @Override
    public short getHeader() {
        return 126; // "A~"
    }
}
