var MarketPlace = Class.create();
MarketPlace.A = null;
MarketPlace.C = null;
MarketPlace.state = 0;
MarketPlace.rotation = 0;
MarketPlace.FurnitureSettings = [];
MarketPlace.Furniture = null;
MarketPlace.B = function(D) {
    Dialog.setAsWaitDialog(MarketPlace.A);
    new Ajax.Request(habboReqPath + "/marketplace/" + D, {
        parameters: MarketPlace.C,
        onComplete: function(F, E) {
            Dialog.setDialogBody(MarketPlace.A, F.responseText)
        }
    })
};

MarketPlace.open = function() {
    MarketPlace.A = Dialog.createDialog("offer_create_form", L10N.get("create.marketplace.offer.title"), 9001, 0, -1000, MarketPlace.close);
    Dialog.setAsWaitDialog(MarketPlace.A);
    Dialog.moveDialogToCenter(MarketPlace.A);
    Dialog.makeDialogDraggable(MarketPlace.A);
    Overlay.show();
    new Ajax.Request(habboReqPath + "/marketplace/offer_create_form", {
        onComplete: function(E, D) {
            Dialog.setDialogBody(MarketPlace.A, E.responseText);
            MarketPlace.noSelection();
        }
    });
};

MarketPlace.remove = function(id) {
    MarketPlace.A = Dialog.createDialog("offer_create_form", L10N.get("confirm.remove.marketplace.offer.title"), 9001, 0, -1000, MarketPlace.close);
    Dialog.setAsWaitDialog(MarketPlace.A);
    Dialog.moveDialogToCenter(MarketPlace.A);
    Dialog.makeDialogDraggable(MarketPlace.A);
    Overlay.show();
    new Ajax.Request(habboReqPath + "/marketplace/remove_offer", {
        parameters: {
            id: id
        },
        onComplete: function(E, D) {
            Dialog.setDialogBody(MarketPlace.A, E.responseText);
        }
    });
};

MarketPlace.purchase = function(id) {
    MarketPlace.A = Dialog.createDialog("offer_create_form", L10N.get("purchase.marketplace.offer.title"), 9001, 0, -1000, MarketPlace.close);
    Dialog.setAsWaitDialog(MarketPlace.A);
    Dialog.moveDialogToCenter(MarketPlace.A);
    Dialog.makeDialogDraggable(MarketPlace.A);
    Overlay.show();
    new Ajax.Request(habboReqPath + "/marketplace/purchase_item", {
        parameters: {
            id: id
        },
        onComplete: function(E, D) {
            Dialog.setDialogBody(MarketPlace.A, E.responseText);
        }
    });
};

MarketPlace.purchaseItem = function(id) {
    MarketPlace.C = {
        id: id
    };
    MarketPlace.B("purchase_confirm_item");
};

MarketPlace.confirmRemoval = function(id) {
    MarketPlace.C = {
        id: id
    };
    MarketPlace.B("confirm_remove_offer");
};

MarketPlace.noSelection = function() {
    document.getElementById("canvas").innerHTML = ("<p style=\"text-align: center; vertical-align: middle;line-height: 170px;\">No furniture selected</p>");
    document.getElementById("canvas-small").innerHTML = ("<p style=\"text-align: center; vertical-align: middle;line-height: 75px;\">No selection</p>");
    document.getElementById("canvas").style.backgroundImage = null;
    document.getElementById("canvas-small").style.backgroundImage = null;

    var btnRotate = document.getElementById("btn-rotate");
    var btnToggleStates = document.getElementById("btn-toggle-status");
    var btnToggles = document.getElementById("toggle-furni");
    var txtPrice = document.getElementById("offer-price");

    btnToggles.style.display = "none";
    btnRotate.style.display = "block";
    btnToggleStates.style.display = "block";
    txtPrice.value = "";

    MarketPlace.Furniture = null;
};


MarketPlace.close = function(D) {
    if (!!D) {
        Event.stop(D)
    }
    $("offer_create_form").remove();
    Overlay.hide();
    MarketPlace.A = null;
    MarketPlace.C = null;
    MarketPlace.state = 0;
    MarketPlace.rotation = 0;
    MarketPlace.FurnitureSettings = [];
    MarketPlace.Furniture = null
};

MarketPlace.rotate = function() {
    var new_rotation = MarketPlace.rotation + 1;

    if (new_rotation > MarketPlace.Furniture.allowed_rotations.length - 1) {
        new_rotation = 0;
    }

    console.log(new_rotation);

    MarketPlace.rotation = new_rotation;
    MarketPlace.previewItem();

};

MarketPlace.toggleState = function() {
    if (MarketPlace.state == 0) {
        MarketPlace.state = 1;
    } else if (MarketPlace.state == 1) {
        MarketPlace.state = 0;
    } else {
        MarketPlace.state = 0;
    }

    MarketPlace.previewItem();
};

MarketPlace.itemSelected = function() {
    var itemId = parseInt($F("item-list"));

    if (itemId && itemId > 0) {
        if (!MarketPlace.FurnitureSettings[itemId]) {
            new Ajax.Request(habboReqPath + "/api/item_settings/" + itemId, {
                onComplete: function(response) {
                    MarketPlace.FurnitureSettings[itemId] = JSON.parse(response.responseText);
                    MarketPlace.resetCanvas(itemId);
                }
            });
        } else {
            MarketPlace.resetCanvas(itemId);
        }

    } else {
        MarketPlace.noSelection();
    }
};

MarketPlace.resetCanvas = function(itemId) {
    MarketPlace.Furniture = MarketPlace.FurnitureSettings[itemId];
    MarketPlace.rotation = 0;
    MarketPlace.state = 0;
    MarketPlace.previewItem();
}

MarketPlace.previewItem = function() {
    var itemId = parseInt($F("item-list"));

    var btnRotate = document.getElementById("btn-rotate");
    var btnToggleStates = document.getElementById("btn-toggle-status");

    if (MarketPlace.Furniture.allowed_rotations.length <= 1) {
        btnRotate.style.display = "none";
    } else {
        btnRotate.style.display = "block";
    }

    if (MarketPlace.Furniture.max_states == 0) {
        btnToggleStates.style.display = "none";
    } else {
        btnToggleStates.style.display = "block";
    }

    var btnToggles = document.getElementById("toggle-furni");
    btnToggles.style.display = "block";

    document.getElementById("canvas").innerHTML = "";
    document.getElementById("canvas").style.backgroundImage = "url('" + furniImagerPath + "?sprite=" + MarketPlace.getSprite() + "&direction=" + MarketPlace.getRotation() + "&canvas=f3f3f3&state=" + MarketPlace.state + "&color=" + MarketPlace.getColour() + "')";
    document.getElementById("canvas").style.backgroundRepeat = "no-repeat";
    document.getElementById("canvas").style.backgroundPosition = "center";

    document.getElementById("canvas-small").innerHTML = "";
    document.getElementById("canvas-small").style.backgroundImage = "url('" + furniImagerPath + "?sprite=" + MarketPlace.getSprite() + "&direction=" + MarketPlace.getRotation() + "&canvas=f3f3f3&state=" + MarketPlace.state + "&color=" + MarketPlace.getColour() + "&small=1')";
    document.getElementById("canvas-small").style.backgroundRepeat = "no-repeat";
    document.getElementById("canvas-small").style.backgroundPosition = "center";
};

MarketPlace.confirm = function() {
    var itemId = parseInt($F("item-list"));
    if (itemId && itemId > 0 && MarketPlace.Furniture) {
        MarketPlace.C = {
            item: itemId,
            name: MarketPlace.Furniture.name,
            description: MarketPlace.Furniture.description,
            sprite: MarketPlace.getSprite(),
            rotation: MarketPlace.getRotation(),
            state: MarketPlace.state,
            color: MarketPlace.getColour(),
            price: parseInt($F("offer-price"))
        };
    } else {
        MarketPlace.C = null;
    }

    MarketPlace.B("purchase_offer_confirmation");
};

MarketPlace.purchaseOffer = function() {
    MarketPlace.B("purchase_offer");
};

MarketPlace.refreshOffers = function() {
    new Ajax.Request(habboReqPath + "/marketplace/my_offers", {
        onComplete: function(response) {
            document.getElementById("tab-1-3-1-content").innerHTML = response.responseText;
        }
    });
};

MarketPlace.searchAdvanced = function() {
    document.getElementById("sort-by-value").value = 'false';
    document.getElementById("sort-by-popularity").value = 'false';

    new Ajax.Request(habboReqPath + "/marketplace/offers?preventCache="+new Date(), {
        parameters: MarketPlace.getSearchArguments(),
        onComplete: function(response) {
            document.getElementById("marketplace-habblet-list-container").innerHTML = response.responseText;
						addEventListeners();
        }
    });
};

MarketPlace.search = function(sortBy) {
    document.getElementById("sort-by-value").value = 'false';
    document.getElementById("sort-by-popularity").value = 'false';
    document.getElementById(sortBy).value = 'true';

    new Ajax.Request(habboReqPath + "/marketplace/offers?preventCache="+new Date(), {
        parameters: MarketPlace.getSearchArguments(),
        onComplete: function(response) {
            document.getElementById("marketplace-habblet-list-container").innerHTML = response.responseText
						addEventListeners();
        }
    });
};

MarketPlace.getSearchArguments = function() {
    arguments = {
        page: $F('current-page'),
        sortByValue: $F('sort-by-value'),
        sortByPopularity: $F('sort-by-popularity'),
        searchName: $F('advanced-search-name'),
        minPrice: $F('advanced-search-min'),
        maxPrice: $F('advanced-search-max'),
        sortOrder: $F('sort-order')
    }

    return arguments
}

MarketPlace.page = function(pageId) {
    document.getElementById('current-page').value = pageId.toString();

    new Ajax.Request(habboReqPath + "/marketplace/offers", {
        parameters: MarketPlace.getSearchArguments(),
        onComplete: function(response) {
            document.getElementById("marketplace-habblet-list-container").innerHTML = response.responseText;
						addEventListeners();
        }
    });
};

MarketPlace.back = function() {
    Dialog.setAsWaitDialog(MarketPlace.A);
    new Ajax.Request(habboReqPath + "/marketplace/offer_create_form", {
        parameters: MarketPlace.C,
        onComplete: function(F, E) {
            Dialog.setDialogBody(MarketPlace.A, F.responseText)

            var itemId = parseInt($F("item-list"));

            if (itemId) {
                if (itemId > 0) {
                    MarketPlace.previewItem();
                } else {
                    MarketPlace.noSelection();
                }
            } else {
                MarketPlace.noSelection();
            }
        }
    })
};

MarketPlace.getColour = function() {
    if (!MarketPlace.Furniture.colour) {
        return 0;
    }

    return MarketPlace.Furniture.colour
};

MarketPlace.getRotation = function() {
    if (MarketPlace.Furniture.allowed_rotations.length == 1) {
        return MarketPlace.Furniture.allowed_rotations[0];
    }

    return MarketPlace.Furniture.allowed_rotations[MarketPlace.rotation]
};

MarketPlace.getSprite = function() {
    return MarketPlace.Furniture.sprite
};

MarketPlace.isNumberKey = function(evt) {
    var charCode = (evt.which) ? evt.which : evt.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode != 37 && charCode != 39 && charCode != 46)
        return false
    return true
};

MarketPlace.pricingVerify = function(evt) {
    var price = parseInt($F("offer-price"));
    if (price) {
        if (price > 100000) {
            document.getElementById("offer-price").value = "100000";
        }

        if (price < 2) {
            document.getElementById("offer-price").value = "2";
        }
    }

}