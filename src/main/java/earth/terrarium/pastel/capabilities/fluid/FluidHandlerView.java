package earth.terrarium.pastel.capabilities.fluid;

import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public class FluidHandlerView implements IFluidHandler {

    private final IFluidHandler delegator;
    private boolean supportsInsertion = true;
    private boolean supportsExtraction = true;

    public FluidHandlerView(IFluidHandler delegator) {
        this.delegator = delegator;
    }

    public FluidHandlerView disableExtraction() {
        this.supportsExtraction = false;
        return this;
    }

    public FluidHandlerView disableInsertion() {
        this.supportsInsertion = false;
        return this;
    }

    @Override
    public int getTanks() {
        return delegator.getTanks();
    }

    @Override
    public FluidStack getFluidInTank(int i) {
        return delegator.getFluidInTank(i);
    }

    @Override
    public int getTankCapacity(int i) {
        return delegator.getTankCapacity(i);
    }

    @Override
    public boolean isFluidValid(int i, FluidStack fluidStack) {
        return delegator.isFluidValid(i, fluidStack);
    }

    @Override
    public int fill(FluidStack fluidStack, FluidAction fluidAction) {
        if (!supportsInsertion)
            return 0;

        return delegator.fill(fluidStack, fluidAction);
    }

    @Override
    public FluidStack drain(FluidStack fluidStack, FluidAction fluidAction) {
        if (!supportsExtraction)
            return FluidStack.EMPTY;

        return delegator.drain(fluidStack, fluidAction);
    }

    @Override
    public FluidStack drain(int i, FluidAction fluidAction) {
        if (!supportsExtraction)
            return FluidStack.EMPTY;

        return delegator.drain(i, fluidAction);
    }
}
