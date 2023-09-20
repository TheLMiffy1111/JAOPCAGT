package thelm.jaopca.gtceu;

import net.minecraftforge.fml.common.Mod;

@Mod(JAOPCAGTCEu.MOD_ID)
public class JAOPCAGTCEu {

	public static final String MOD_ID = "jaopcagtceu";
	public static JAOPCAGTCEu core;
	public static boolean mixinLoaded = false;

	public JAOPCAGTCEu() {
		assert mixinLoaded;
		core = this;
	}
}
