package thelm.jaopca.gtceu.compat.gtceu;

import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.gregtechceu.gtceu.api.recipe.GTRecipeType;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import thelm.jaopca.api.ingredients.CompoundIngredientObject;
import thelm.jaopca.gtceu.compat.gtceu.recipes.GTRecipeSerializer;
import thelm.jaopca.gtceu.compat.gtceu.recipes.GTRecipeSettings;
import thelm.jaopca.utils.ApiImpl;
import thelm.jaopca.utils.MiscHelper;

public class GTCEuHelper {

	public static final GTCEuHelper INSTANCE = new GTCEuHelper();

	private GTCEuHelper() {}

	public Ingredient getIngredient(Object obj) {
		if(obj instanceof CompoundIngredientObject compound) {
			Pair<Ingredient, Set<Item>> ing = MiscHelper.INSTANCE.getIngredientResolved(compound);
			if(ing.getRight().isEmpty()) {
				return null;
			}
			return Ingredient.of(ing.getRight().toArray(ItemLike[]::new));
		}
		return MiscHelper.INSTANCE.getIngredient(obj);
	}

	public GTRecipeSettings recipeSettings() {
		return new GTRecipeSettings();
	}

	public boolean registerGTRecipe(ResourceLocation key, GTRecipeType recipeType, GTRecipeSettings settings) {
		return ApiImpl.INSTANCE.registerRecipe(key, new GTRecipeSerializer(key, recipeType, settings));
	}

	public boolean registerGTRecipe(ResourceLocation key, String recipeType, GTRecipeSettings settings) {
		return ApiImpl.INSTANCE.registerRecipe(key, new GTRecipeSerializer(key, recipeType, settings));
	}
}
