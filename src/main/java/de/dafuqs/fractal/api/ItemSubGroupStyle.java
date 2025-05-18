package de.dafuqs.fractal.api;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public record ItemSubGroupStyle(@Nullable ResourceLocation backgroundTexture,
								
								@Nullable ResourceLocation selectedSubtabTextureLeft,
								@Nullable ResourceLocation unselectedSubtabTextureLeft,
								@Nullable ResourceLocation selectedSubtabTextureRight,
								@Nullable ResourceLocation unselectedSubtabTextureRight,
								
								@Nullable ResourceLocation enabledScrollbarTexture,
								@Nullable ResourceLocation disabledScrollbarTexture,
								
								@Nullable ResourceLocation tabTopFirstSelectedTexture,
								@Nullable ResourceLocation tabTopSelectedTexture,
								@Nullable ResourceLocation tabTopLastSelectedTexture,
								@Nullable ResourceLocation tabTopFirstUnselectedTexture,
								@Nullable ResourceLocation tabTopUnselectedTexture,
								@Nullable ResourceLocation tabTopLastUnselectedTexture,
								@Nullable ResourceLocation tabBottomFirstSelectedTexture,
								@Nullable ResourceLocation tabBottomSelectedTexture,
								@Nullable ResourceLocation tabBottomLastSelectedTexture,
								@Nullable ResourceLocation tabBottomFirstUnselectedTexture,
								@Nullable ResourceLocation tabBottomUnselectedTexture,
								@Nullable ResourceLocation tabBottomLastUnselectedTexture) {
	
	public static final ResourceLocation DEFAULT_SUBTAB_SELECTED_TEXTURE_LEFT = ResourceLocation.fromNamespaceAndPath("fractal", "container/creative_inventory/subtab_selected_left");
	public static final ResourceLocation DEFAULT_SUBTAB_UNSELECTED_TEXTURE_LEFT = ResourceLocation.fromNamespaceAndPath("fractal", "container/creative_inventory/subtab_unselected_left");
	public static final ResourceLocation DEFAULT_SUBTAB_SELECTED_TEXTURE_RIGHT = ResourceLocation.fromNamespaceAndPath("fractal", "container/creative_inventory/subtab_selected_right");
	public static final ResourceLocation DEFAULT_SUBTAB_UNSELECTED_TEXTURE_RIGHT = ResourceLocation.fromNamespaceAndPath("fractal", "container/creative_inventory/subtab_unselected_right");
	
	public static class Builder {
		
		protected @Nullable ResourceLocation backgroundTexture;
		
		protected @Nullable ResourceLocation selectedSubtabTextureLeft = DEFAULT_SUBTAB_SELECTED_TEXTURE_LEFT;
		protected @Nullable ResourceLocation unselectedSubtabTextureLeft = DEFAULT_SUBTAB_UNSELECTED_TEXTURE_LEFT;
		protected @Nullable ResourceLocation selectedSubtabTextureRight = DEFAULT_SUBTAB_SELECTED_TEXTURE_RIGHT;
		protected @Nullable ResourceLocation unselectedSubtabTextureRight = DEFAULT_SUBTAB_UNSELECTED_TEXTURE_RIGHT;
		
		protected @Nullable ResourceLocation enabledScrollbarTexture;
		protected @Nullable ResourceLocation disabledScrollbarTexture;
		
		protected @Nullable ResourceLocation tabTopFirstSelectedTexture;
		protected @Nullable ResourceLocation tabTopSelectedTexture;
		protected @Nullable ResourceLocation tabTopLastSelectedTexture;
		protected @Nullable ResourceLocation tabTopFirstUnselectedTexture;
		protected @Nullable ResourceLocation tabTopUnselectedTexture;
		protected @Nullable ResourceLocation tabTopLastUnselectedTexture;
		
		protected @Nullable ResourceLocation tabBottomFirstSelectedTexture;
		protected @Nullable ResourceLocation tabBottomSelectedTexture;
		protected @Nullable ResourceLocation tabBottomLastSelectedTexture;
		protected @Nullable ResourceLocation tabBottomFirstUnselectedTexture;
		protected @Nullable ResourceLocation tabBottomUnselectedTexture;
		protected @Nullable ResourceLocation tabBottomLastUnselectedTexture;
		
		public Builder() {
		}
		
		public Builder background(ResourceLocation backgroundTexture) { // texture
			this.backgroundTexture = backgroundTexture;
			return this;
		}
		
		public Builder scrollbar(ResourceLocation enabledTexture, ResourceLocation disabledTexture) { // sprite
			this.enabledScrollbarTexture = enabledTexture;
			this.disabledScrollbarTexture = disabledTexture;
			return this;
		}
		
		public Builder subtab(ResourceLocation selectedSubtabTextureLeft, ResourceLocation unselectedSubtabTextureLeft, ResourceLocation selectedSubtabTextureRight, ResourceLocation unselectedSubtabTextureRight) { // sprite
			this.selectedSubtabTextureLeft = selectedSubtabTextureLeft;
			this.unselectedSubtabTextureLeft = unselectedSubtabTextureLeft;
			this.selectedSubtabTextureRight = selectedSubtabTextureRight;
			this.unselectedSubtabTextureRight = unselectedSubtabTextureRight;
			return this;
		}
		
		public Builder tab(ResourceLocation topFirstSelectedTexture, ResourceLocation topSelectedTexture, ResourceLocation topLastSelectedTexture, ResourceLocation topFirstUnselectedTexture, ResourceLocation topUnselectedTexture, ResourceLocation topLastUnselectedTexture,
						   ResourceLocation bottomFirstSelectedTexture, ResourceLocation bottomSelectedTexture, ResourceLocation bottomLastSelectedTexture, ResourceLocation bottomFirstUnselectedTexture, ResourceLocation bottomUnselectedTexture, ResourceLocation bottomLastUnselectedTexture) { // sprite
			
			this.tabTopFirstSelectedTexture = topFirstSelectedTexture;
			this.tabTopSelectedTexture = topSelectedTexture;
			this.tabTopLastSelectedTexture = topLastSelectedTexture;
			this.tabTopFirstUnselectedTexture = topFirstUnselectedTexture;
			this.tabTopUnselectedTexture = topUnselectedTexture;
			this.tabTopLastUnselectedTexture = topLastUnselectedTexture;
			
			this.tabBottomFirstSelectedTexture = bottomFirstSelectedTexture;
			this.tabBottomSelectedTexture = bottomSelectedTexture;
			this.tabBottomLastSelectedTexture = bottomLastSelectedTexture;
			this.tabBottomFirstUnselectedTexture = bottomFirstUnselectedTexture;
			this.tabBottomUnselectedTexture = bottomUnselectedTexture;
			this.tabBottomLastUnselectedTexture = bottomLastUnselectedTexture;
			
			return this;
		}
		
		public ItemSubGroupStyle build() {
			return new ItemSubGroupStyle(backgroundTexture,
					selectedSubtabTextureLeft, unselectedSubtabTextureLeft, selectedSubtabTextureRight, unselectedSubtabTextureRight,
					enabledScrollbarTexture, disabledScrollbarTexture,
					tabTopFirstSelectedTexture, tabTopSelectedTexture, tabTopLastSelectedTexture, tabTopFirstUnselectedTexture, tabTopUnselectedTexture, tabTopLastUnselectedTexture,
					tabBottomFirstSelectedTexture, tabBottomSelectedTexture, tabBottomLastSelectedTexture, tabBottomFirstUnselectedTexture, tabBottomUnselectedTexture, tabBottomLastUnselectedTexture);
		}
	}
	
}
