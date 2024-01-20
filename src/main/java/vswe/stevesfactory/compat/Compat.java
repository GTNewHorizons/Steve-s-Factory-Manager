package vswe.stevesfactory.compat;

import cpw.mods.fml.common.Loader;

public class Compat {

    public static boolean HAS_ADDONS = Loader.isModLoaded("StevesAddons");
    public static Hooks ADDONS_HOOKS = null;
}
