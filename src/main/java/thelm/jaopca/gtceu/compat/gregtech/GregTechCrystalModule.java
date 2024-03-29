package thelm.jaopca.gtceu.compat.gregtech;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimap;

import gregtech.api.recipes.RecipeMaps;
import gregtech.common.ConfigHolder;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.forms.IForm;
import thelm.jaopca.api.forms.IFormRequest;
import thelm.jaopca.api.helpers.IMiscHelper;
import thelm.jaopca.api.items.IItemFormType;
import thelm.jaopca.api.items.IItemInfo;
import thelm.jaopca.api.materials.IMaterial;
import thelm.jaopca.api.materials.MaterialType;
import thelm.jaopca.api.modules.IModule;
import thelm.jaopca.api.modules.IModuleData;
import thelm.jaopca.api.modules.JAOPCAModule;
import thelm.jaopca.gtceu.compat.gregtech.recipes.GregTechRecipeSettings;
import thelm.jaopca.items.ItemFormType;
import thelm.jaopca.utils.ApiImpl;
import thelm.jaopca.utils.MiscHelper;

@JAOPCAModule(modDependencies = "gregtech@[2.7,)")
public class GregTechCrystalModule implements IModule {

	static final Set<String> BLACKLIST = GregTechModule.BLACKLIST;

	private final IForm exquisiteGemForm = ApiImpl.INSTANCE.newForm(this, "gregtech_exquisite_gem", ItemFormType.INSTANCE).
			setMaterialTypes(MaterialType.GEM, MaterialType.CRYSTAL).setSecondaryName("gemExquisite").setDefaultMaterialBlacklist(BLACKLIST);
	private final IForm flawlessGemForm = ApiImpl.INSTANCE.newForm(this, "gregtech_flawless_gem", ItemFormType.INSTANCE).
			setMaterialTypes(MaterialType.GEM, MaterialType.CRYSTAL).setSecondaryName("gemFlawless").setDefaultMaterialBlacklist(BLACKLIST);
	private final IForm flawedGemForm = ApiImpl.INSTANCE.newForm(this, "gregtech_flawed_gem", ItemFormType.INSTANCE).
			setMaterialTypes(MaterialType.GEM, MaterialType.CRYSTAL).setSecondaryName("gemFlawed").setDefaultMaterialBlacklist(BLACKLIST);
	private final IForm chippedGemForm = ApiImpl.INSTANCE.newForm(this, "gregtech_chipped_gem", ItemFormType.INSTANCE).
			setMaterialTypes(MaterialType.GEM, MaterialType.CRYSTAL).setSecondaryName("gemChipped").setDefaultMaterialBlacklist(BLACKLIST);
	private final IFormRequest formRequest = ApiImpl.INSTANCE.newFormRequest(this,
			exquisiteGemForm, flawlessGemForm, flawedGemForm, chippedGemForm).setGrouped(true);

	@Override
	public String getName() {
		return "gregtech_crystal";
	}

	@Override
	public Multimap<Integer, String> getModuleDependencies() {
		ImmutableSetMultimap.Builder<Integer, String> builder = ImmutableSetMultimap.builder();
		builder.put(0, "gregtech");
		builder.put(0, "dust");
		builder.put(0, "small_dust");
		return builder.build();
	}

	@Override
	public List<IFormRequest> getFormRequests() {
		return Collections.singletonList(formRequest);
	}

	@Override
	public Set<MaterialType> getMaterialTypes() {
		return EnumSet.of(MaterialType.GEM, MaterialType.CRYSTAL);
	}

	@Override
	public Set<String> getDefaultMaterialBlacklist() {
		return BLACKLIST;
	}

	@Override
	public void onInit(IModuleData moduleData, FMLInitializationEvent event) {
		JAOPCAApi api = ApiImpl.INSTANCE;
		GregTechHelper helper = GregTechHelper.INSTANCE;
		IMiscHelper miscHelper = MiscHelper.INSTANCE;
		IItemFormType itemFormType = ItemFormType.INSTANCE;
		for(IMaterial material : formRequest.getMaterials()) {
			String name = material.getName();

			IItemInfo exquisiteGemInfo = itemFormType.getMaterialFormInfo(exquisiteGemForm, material);
			String exquisiteGemOredict = miscHelper.getOredictName("gemExquisite", name);
			IItemInfo flawlessGemInfo = itemFormType.getMaterialFormInfo(flawlessGemForm, material);
			String flawlessGemOredict = miscHelper.getOredictName("gemFlawless", name);
			IItemInfo flawedGemInfo = itemFormType.getMaterialFormInfo(flawedGemForm, material);
			String flawedGemOredict = miscHelper.getOredictName("gemFlawed", name);
			IItemInfo chippedGemInfo = itemFormType.getMaterialFormInfo(chippedGemForm, material);
			String chippedGemOredict = miscHelper.getOredictName("gemChipped", name);
			String purifiedCrushedOredict = miscHelper.getOredictName("crushedPurified", name);
			String materialOredict = miscHelper.getOredictName(material.getType().getFormName(), name);
			String dustOredict = miscHelper.getOredictName("dust", name);
			String smallDustOredict = miscHelper.getOredictName("dustSmall", name);

			{
				GregTechRecipeSettings<?> settings = helper.recipeSettings(RecipeMaps.SIFTER_RECIPES).
						input(purifiedCrushedOredict, 1).
						output(exquisiteGemInfo, 1, 500, 150).
						output(flawlessGemInfo, 1, 1500, 200).
						output(materialOredict, 1, 5000, 1000).
						output(dustOredict, 1, 2500, 500).
						time(400).energy(16);
				if(ConfigHolder.recipes.generateLowQualityGems) {
					settings.
					output(flawedGemInfo, 1, 2000, 500).
					output(chippedGemInfo, 1, 3000, 350);
				}
				helper.registerGregTechRecipe(
						miscHelper.getRecipeKey("gregtech.purified_crushed_to_gems", name),
						settings);
			}

			helper.registerGregTechRecipe(
					miscHelper.getRecipeKey("gregtech.flawless_gem_to_exquisite_gem", name),
					helper.recipeSettings(RecipeMaps.LASER_ENGRAVER_RECIPES).
					input(flawlessGemOredict, 2).
					input("craftingLensWhite", 0).
					output(exquisiteGemInfo, 1).
					time(300).energy(240));

			api.registerShapelessRecipe(
					miscHelper.getRecipeKey("gregtech.exquisite_gem_to_flawless_gem_hard_hammer", name),
					flawlessGemInfo, 2, new Object[] {
							"craftingToolHardHammer", exquisiteGemOredict,
					});
			helper.registerGregTechRecipe(
					miscHelper.getRecipeKey("gregtech.exquisite_gem_to_flawless_gem_cutter", name),
					helper.recipeSettings(RecipeMaps.CUTTER_RECIPES).
					input(exquisiteGemOredict, 1).
					output(flawlessGemInfo, 2).
					time(20).energy(16));
			helper.registerGregTechRecipe(
					miscHelper.getRecipeKey("gregtech.material_to_flawless_gem", name),
					helper.recipeSettings(RecipeMaps.LASER_ENGRAVER_RECIPES).
					input(materialOredict, 2).
					input("craftingLensWhite", 0).
					output(flawlessGemInfo, 1).
					time(300).energy(240));

			api.registerShapelessRecipe(
					miscHelper.getRecipeKey("gregtech.flawless_gem_to_material_hard_hammer", name),
					materialOredict, 2, new Object[] {
							"craftingToolHardHammer", flawlessGemOredict,
					});
			helper.registerGregTechRecipe(
					miscHelper.getRecipeKey("gregtech.flawless_gem_to_material_cutter", name),
					helper.recipeSettings(RecipeMaps.CUTTER_RECIPES).
					input(flawlessGemOredict, 1).
					output(materialOredict, 2).
					time(20).energy(16));
			helper.registerGregTechRecipe(
					miscHelper.getRecipeKey("gregtech.flawed_gem_to_material", name),
					helper.recipeSettings(RecipeMaps.LASER_ENGRAVER_RECIPES).
					input(flawedGemOredict, 2).
					input("craftingLensWhite", 0).
					output(materialOredict, 1).
					time(300).energy(240));

			api.registerShapelessRecipe(
					miscHelper.getRecipeKey("gregtech.material_to_flawed_gem_hard_hammer", name),
					flawedGemOredict, 2, new Object[] {
							"craftingToolHardHammer", materialOredict,
					});
			helper.registerGregTechRecipe(
					miscHelper.getRecipeKey("gregtech.material_to_flawed_gem_cutter", name),
					helper.recipeSettings(RecipeMaps.CUTTER_RECIPES).
					input(materialOredict, 1).
					output(flawedGemOredict, 2).
					time(20).energy(16));
			helper.registerGregTechRecipe(
					miscHelper.getRecipeKey("gregtech.chipped_gem_to_flawed_gem", name),
					helper.recipeSettings(RecipeMaps.LASER_ENGRAVER_RECIPES).
					input(chippedGemOredict, 2).
					input("craftingLensWhite", 0).
					output(flawedGemOredict, 1).
					time(300).energy(240));

			api.registerShapelessRecipe(
					miscHelper.getRecipeKey("gregtech.flawed_gem_to_chipped_gem_hard_hammer", name),
					chippedGemOredict, 2, new Object[] {
							"craftingToolHardHammer", flawedGemOredict,
					});
			helper.registerGregTechRecipe(
					miscHelper.getRecipeKey("gregtech.flawed_gem_to_chipped_gem_cutter", name),
					helper.recipeSettings(RecipeMaps.CUTTER_RECIPES).
					input(flawedGemOredict, 1).
					output(chippedGemOredict, 2).
					time(20).energy(16));

			helper.registerGregTechRecipe(
					miscHelper.getRecipeKey("gregtech.exquisite_gem_to_dust", name),
					helper.recipeSettings(RecipeMaps.MACERATOR_RECIPES).
					input(exquisiteGemOredict, 1).
					output(dustOredict, 4).
					time(400).energy(2));
			helper.registerGregTechRecipe(
					miscHelper.getRecipeKey("gregtech.flawless_gem_to_dust", name),
					helper.recipeSettings(RecipeMaps.MACERATOR_RECIPES).
					input(flawlessGemOredict, 1).
					output(dustOredict, 2).
					time(200).energy(2));
			helper.registerGregTechRecipe(
					miscHelper.getRecipeKey("gregtech.flawed_gem_to_small_dust", name),
					helper.recipeSettings(RecipeMaps.MACERATOR_RECIPES).
					input(flawedGemOredict, 1).
					output(smallDustOredict, 2).
					time(50).energy(2));
			helper.registerGregTechRecipe(
					miscHelper.getRecipeKey("gregtech.chipped_gem_to_small_dust", name),
					helper.recipeSettings(RecipeMaps.MACERATOR_RECIPES).
					input(chippedGemOredict, 1).
					output(smallDustOredict, 1).
					time(25).energy(2));
		}
	}
}
