package com.ervic.mac.gobvalle.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ervic on 11/24/17.
 */

public class Data {
    public List<BottomMenu> bottomMenu = null;
    public List<leftMenu> leftMenu = null;
    public List<careServicesMenu> careServicesMenu = null;

    public List<BottomMenu> getBottomMenu() {
        return bottomMenu;
    }

    public void setBottomMenu(List<BottomMenu> bottomMenu) {
        this.bottomMenu = bottomMenu;
    }

    public List<com.ervic.mac.gobvalle.Models.leftMenu> getLeftMenu() {
        return leftMenu;
    }

    public void setLeftMenu(List<com.ervic.mac.gobvalle.Models.leftMenu> leftMenu) {
        this.leftMenu = leftMenu;
    }

    public List<com.ervic.mac.gobvalle.Models.careServicesMenu> getCareServicesMenu() {
        return careServicesMenu;
    }

    public void setCareServicesMenu(List<com.ervic.mac.gobvalle.Models.careServicesMenu> careServicesMenu) {
        this.careServicesMenu = careServicesMenu;
    }
}
