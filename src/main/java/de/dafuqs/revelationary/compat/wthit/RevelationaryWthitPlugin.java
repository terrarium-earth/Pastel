package de.dafuqs.revelationary.compat.wthit;

import de.dafuqs.revelationary.api.revelations.RevelationAware;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;

public class RevelationaryWthitPlugin implements IWailaPlugin {
    @Override
    public void register(IRegistrar registrar) {
        registrar.addOverride(new CloakedBlockComponentProvider(), RevelationAware.class);
    }
}
