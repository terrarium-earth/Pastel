package de.dafuqs.revelationary.compat.wthit;

import de.dafuqs.revelationary.api.revelations.RevelationAware;
import mcp.mobius.waila.api.IClientRegistrar;
import mcp.mobius.waila.api.IWailaClientPlugin;

public class RevelationaryWthitPlugin implements IWailaClientPlugin {
    @Override
    public void register(IClientRegistrar registrar) {
        registrar.override(new CloakedBlockComponentProvider(), RevelationAware.class);
    }
}
