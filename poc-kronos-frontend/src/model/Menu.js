import appDrawerItems from "../data/app-drawer-items";

class Menu {
  static create(authorities) {
    let items = [{ header: "GESTÃO EDUCACIONAL" }];
    Object.keys(authorities).map(key => {
      const menus = authorities[key];
      menus.map(m => {
        const item = appDrawerItems.filter(i => i.menu === m)[0];
        if (item && !items.includes(item)) {
          items.push(item);
        }
      });
    });
    return items;
  }
}
export default Menu;
