package thelm.jaopca.gtceu.compat.gtceu.recipes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalLong;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import com.gregtechceu.gtceu.api.recipe.RecipeCondition;
import com.gregtechceu.gtceu.api.recipe.category.GTRecipeCategory;
import com.gregtechceu.gtceu.api.recipe.ingredient.IntCircuitIngredient;
import com.gregtechceu.gtceu.common.item.IntCircuitBehaviour;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.StrictNBTIngredient;

public class GTRecipeSettings {

	public OptionalLong euInput = OptionalLong.empty();
	public OptionalLong euOutput = OptionalLong.empty();
	public OptionalLong euTick = OptionalLong.empty();
	public OptionalInt cwuInput = OptionalInt.empty();
	public OptionalInt cwuOutput = OptionalInt.empty();
	public OptionalInt cwuTick = OptionalInt.empty();
	public OptionalInt cwuTotal = OptionalInt.empty();
	public List<Pair<Object, Triple<Integer, Integer, Integer>>> itemInput = new ArrayList<>();
	public List<Pair<Object, Triple<Integer, Integer, Integer>>> itemOutput = new ArrayList<>();
	public List<Pair<Object, Triple<Integer, Integer, Integer>>> fluidInput = new ArrayList<>();
	public List<Pair<Object, Triple<Integer, Integer, Integer>>> fluidOutput = new ArrayList<>();
	public CompoundTag data = new CompoundTag();
	public List<RecipeCondition> conditions = new ArrayList<>();
	public OptionalInt duration = OptionalInt.empty();
	public Optional<GTRecipeCategory> category = Optional.empty();

	public GTRecipeSettings euInput(long eu) {
		euInput = OptionalLong.of(eu);
		return this;
	}

	public GTRecipeSettings euOutput(long eu) {
		euOutput = OptionalLong.of(eu);
		return this;
	}

	public GTRecipeSettings EUt(long eu) {
		euTick = OptionalLong.of(eu);
		return this;
	}

	public GTRecipeSettings cwuInput(int cwu) {
		cwuInput = OptionalInt.of(cwu);
		return this;
	}

	public GTRecipeSettings cwuOutput(int cwu) {
		cwuOutput = OptionalInt.of(cwu);
		return this;
	}

	public GTRecipeSettings CWUt(int cwu) {
		cwuTick = OptionalInt.of(cwu);
		return this;
	}

	public GTRecipeSettings cwuTotal(int cwu) {
		cwuTotal = OptionalInt.of(cwu);
		return this;
	}

	public GTRecipeSettings itemInput(Object input) {
		return itemInput(input, 1, 10000, 0);
	}

	public GTRecipeSettings itemInput(Object input, int count) {
		return itemInput(input, count, 10000, 0);
	}

	public GTRecipeSettings itemInput(Object input, int chance, int tierChanceBoost) {
		return itemInput(input, 1, chance, tierChanceBoost);
	}

	public GTRecipeSettings itemInput(Object input, int count, int chance, int tierChanceBoost) {
		itemInput.add(Pair.of(input, Triple.of(count, chance, tierChanceBoost)));
		return this;
	}

	public GTRecipeSettings notConsumable(Object input) {
		return itemInput(input, 1, 0, 0);
	}

	public GTRecipeSettings circuitMeta(int configuration) {
		return notConsumable(IntCircuitIngredient.of(configuration));
	}

	public GTRecipeSettings explosivesAmount(int explosivesAmount) {
		return itemInput(new ItemStack(Blocks.TNT, explosivesAmount));
	}

	public GTRecipeSettings itemOutput(Object output) {
		return itemOutput(output, 1, 10000, 0);
	}

	public GTRecipeSettings itemOutput(Object output, int count) {
		return itemOutput(output, count, 10000, 0);
	}

	public GTRecipeSettings itemOutput(Object output, int chance, int tierChanceBoost) {
		return itemOutput(output, 1, chance, tierChanceBoost);
	}

	public GTRecipeSettings itemOutput(Object output, int count, int chance, int tierChanceBoost) {
		itemOutput.add(Pair.of(output, Triple.of(count, chance, tierChanceBoost)));
		return this;
	}

	public GTRecipeSettings fluidInput(Object input, int amount) {
		return fluidInput(input, amount, 10000, 0);
	}

	public GTRecipeSettings fluidInput(Object input, int amount, int chance, int tierChanceBoost) {
		fluidInput.add(Pair.of(input, Triple.of(amount, chance, tierChanceBoost)));
		return this;
	}

    public GTRecipeSettings notConsumableFluid(Object input, int amount) {
		return fluidInput(input, amount, 0, 0);
    }

	public GTRecipeSettings fluidOutput(Object output, int amount) {
		return fluidOutput(output, amount, 10000, 0);
	}

	public GTRecipeSettings fluidOutput(Object output, int amount, int chance, int tierChanceBoost) {
		fluidOutput.add(Pair.of(output, Triple.of(amount, chance, tierChanceBoost)));
		return this;
	}

	public GTRecipeSettings addData(String key, Tag data) {
		this.data.put(key, data);
		return this;
	}

	public GTRecipeSettings addData(String key, int data) {
		this.data.putInt(key, data);
		return this;
	}

	public GTRecipeSettings addData(String key, long data) {
		this.data.putLong(key, data);
		return this;
	}

	public GTRecipeSettings addData(String key, String data) {
		this.data.putString(key, data);
		return this;
	}

	public GTRecipeSettings addData(String key, Float data) {
		this.data.putFloat(key, data);
		return this;
	}

	public GTRecipeSettings addData(String key, boolean data) {
		this.data.putBoolean(key, data);
		return this;
	}

	public GTRecipeSettings addCondition(RecipeCondition condition) {
		conditions.add(condition);
		return this;
	}

	public GTRecipeSettings duration(int duration) {
		this.duration = OptionalInt.of(duration);
		return this;
	}

	public GTRecipeSettings category(GTRecipeCategory category) {
		this.category = Optional.of(category);
		return this;
	}
}
