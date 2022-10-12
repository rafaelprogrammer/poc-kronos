<template>
  <v-navigation-drawer
    right
    fixed
    temporary
    floating
    v-model="drawerValue"
    class="elevation-1 pb-0"
    hide-overlay
  >
    <div>
      <!-- Settings drawer heading and close button -->
      <v-toolbar flat class="text-xs-center" dark color="grey darken-4">
        <v-list>
          <v-list-tile>
            <v-list-tile-title class="title">Settings</v-list-tile-title>
            <v-list-tile-content></v-list-tile-content>
            <v-list-tile-action>
              <v-btn icon @click.native.stop="$emit('toggleSubDrawer', false)">
                <v-icon>close</v-icon>
              </v-btn>
            </v-list-tile-action>
          </v-list-tile>
        </v-list>
      </v-toolbar>
      <v-divider></v-divider>

      <!-- Aligment Settings -->
      <v-toolbar flat dense>
        <v-toolbar-title>Color Scheme</v-toolbar-title>
      </v-toolbar>
      <color-scheme></color-scheme>
      <v-divider></v-divider>
      <!-- Aligment Settings -->
      <v-toolbar flat dense>
        <v-toolbar-title>Alignments</v-toolbar-title>
      </v-toolbar>
      <alignment-settings></alignment-settings>
      <v-divider></v-divider>

      <!-- Visibility Setting -->
      <v-toolbar flat dense>
        <v-toolbar-title>Display</v-toolbar-title>
      </v-toolbar>
      <visibility-settings></visibility-settings>
      <v-divider></v-divider>

      <!-- Theme builder -->
      <v-toolbar flat dense>
        <v-toolbar-title>Theme Builder</v-toolbar-title>
      </v-toolbar>
      <scheme-settings></scheme-settings>
      <sideba-variant-mod></sideba-variant-mod>
      <v-divider></v-divider>

      <!-- Tranperent Sidebar -->
      <v-toolbar flat dense>
        <v-toolbar-title>Sidebar transparent</v-toolbar-title>
      </v-toolbar>
      <drawer-alpha-mod></drawer-alpha-mod>
      <sidebar-alpha-img imgHeight="100px" flexSize="3"></sidebar-alpha-img>
    </div>
  </v-navigation-drawer>
</template>
<script>
import SidebarAlphaImg from "@/components/Settings/SidebarAlphaImgSetting";
import SchemeSettings from "@/components/Settings/SchemeSettings";
import DrawerAlphaMod from "@/components/Settings/SidebarToggleAlphaMod";
import AlignmentSettings from "@/components/Settings/AlignmentSettings";
import VisibilitySettings from "@/components/Settings/VisibilitySettings";
import ColorScheme from "@/components/Settings/ColorScheme";
import SidebaVariantMod from "@/components/Settings/SidebaVariantMod";

export default {
  props: ["drawer"],
  components: {
    DrawerAlphaMod,
    SchemeSettings,
    SidebarAlphaImg,
    AlignmentSettings,
    VisibilitySettings,
    ColorScheme,
    SidebaVariantMod
  },
  computed: {
    drawerValue: {
      get() {
        return this.drawer;
      },
      set(value) {
        this.$emit("toggleSubDrawer", value);
        return value;
      }
    }
  }
};
</script>
