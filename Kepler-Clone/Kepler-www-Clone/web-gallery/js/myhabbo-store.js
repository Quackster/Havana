var WebStore = {
  
  created: false, 
  dialog: null, 
  itemsDiv: null, 
  tabList: null,
  selectedItem: null, 
  selectedCategory: null, 
  selectedSubCategory: null, 
  selectedTab: null, 
  loadingCategory: false, 
  previewItemPointer: 0, 
  previewItems: null, 
  originalBg: null,
  storeOpened: false,
  backgroundPreviewWarning: false, 
  localization: null, 
  
  open: function(tab) {
    if (!WebStore.created) {
      WebStore._create();
      WebStore._loadMainContent(tab);
    } else {
      WebStore._selectTab(tab);
    }
    
    WebStore.originalBg = $("mypage-bg").className;
    
    WebStore.dialog.show();
    Overlay.show();
    Dialog.moveDialogToCenter(WebStore.dialog);
  }, 
  
  close: function() {
    WebStore.dialog.style.left = "-1500px";
    WebStore.dialog.hide();
    Overlay.hide();
    
    WebStore.Inventory.newItems = [];
    WebStore.Inventory.updateNewItemCount(0);
    
    $("mypage-bg").className = WebStore.originalBg;
    
    WebStore.loadingCategory = false;
    WebStore.Inventory.loadingCategory = false;
  }, 
  
  openSubCategory: function(subCategoryId, categoryId, force, productId) {
    if ((!WebStore.loadingCategory && subCategoryId != WebStore.selectedSubCategory) || force) {
      WebStore.loadingCategory = true;
      WebStore._resetState();
      Element.wait($("webstore-items"));
      WebStore._loadSubCategory(categoryId, subCategoryId, productId);
    }
  }, 
  
  closeConfirmation: function(e) {
    if (e) {
      Event.stop(e);
    }
    if ($("webstore-confirm")) {
      Element.remove($("webstore-confirm"));
      Overlay.move("9000");
    }
  }, 
  
  showWebStoreDivs: function() {
    WebStore.Inventory.hideInventoryDivs();
    $("webstore-categories-container").style.display = "block";
    $("webstore-content-container").style.display = "block";
    $("webstore-items-container").style.display = "block";
    $("webstore-preview-container").style.display = "block";
  }, 
  
  hideWebStoreDivs: function() {
    $("webstore-categories-container").style.display = "none";
    $("webstore-content-container").style.display = "none";
    $("webstore-items-container").style.display = "none";
    $("webstore-preview-container").style.display = "none";
  }, 
  
  _create: function() {
    this.dialog = Dialog.createDialog("purchase-main-dialog", false, false, -1500, 0, function(e) {
      if (e) { Event.stop(e); }
      WebStore.close();
    }, Builder.node("div", [ Builder.node("ul", { id:"webstore-tabs" }) ]).innerHTML);
    new Draggable(this.dialog, { handle:'webstore-tabs', starteffect:Prototype.emptyFunction, endeffect:Prototype.emptyFunction, zindex:9100 });
    Dialog.setAsWaitDialog(WebStore.dialog);
  }, 
  
  _loadMainContent: function(tab) {
    if (tab == "webstore-inventory") {
      WebStore.Inventory.load();
    } else {
      new Ajax.Request(
        habboReqPath + "/myhabbo/store/main", {
          method: "post",
          onComplete: function(req, json) {
            if (WebStore._checkResponse(req.responseText)) {
              Dialog.setDialogBody(WebStore.dialog, req.responseText);
              WebStore.created = true;
              WebStore.storeOpened = true;
              WebStore.MenuBar.init();
              WebStore.Inventory.initMenu();
              
              WebStore.localization = json[0];
              WebStore._buildTabs();
              WebStore._selectTab("webstore-store");
              
              if (WebStore.MenuBar.selectedMainCategory) {
                WebStore._setContentClassName(WebStore.MenuBar.selectedMainCategory.id.split("-").last());
              }
              
              if (json[1] && json[1].length > 0) {
                WebStore.previewItems = json[1];
                WebStore._showPreview();
                
                var el = $A($("webstore-item-list").getElementsByTagName("li")).first();
                if (el) { WebStore._selectItem(el); }
              }
              
              WebStore._setEventHandlers();
            }
          }
        }
      );
    }
  }, 
  
  _setEventHandlers: function() {
    if ($("webstore-items")) {
      $("webstore-items").onclick = WebStore._handleItemClick.bindAsEventListener(this);
    }
    
    if ($("webstore-preview")) {
      $("webstore-preview").onclick = WebStore._handlePreviewClick.bindAsEventListener(this);
    }
    
    if ($("webstore-close")) {
      $("webstore-close").onclick = function(e) { Event.stop(e); WebStore.close(); }.bindAsEventListener(this);
    }
    
    if (WebStore.tabList) {
      WebStore.tabList.onclick = WebStore._handleTabClick.bindAsEventListener(this);
    }
    
    WebStore.Inventory._setEventHandlers();
  }, 
  
  _handleItemClick: function(e) {
    var el = Event.findElement(e, "li");
    if (el && el.id && el != WebStore.selectedItem) {
      WebStore._selectItem(el);
      WebStore._loadPreview(el.id.substring(el.id.lastIndexOf("-")+1));
    }
    Event.stop(e);
  }, 
  
  _selectItem: function(el) {
    try {
      if (!Element.hasClassName(el, "selected")) {
        Element.removeClassName(WebStore.selectedItem, "selected");
        Element.addClassName(el, "selected");
        WebStore.selectedItem = el;
      }
    } catch (ex) {}
  }, 
  
  _handlePreviewClick: function(e) {
    var el = Event.findElement(e, "a");
    if (el && el.id) {
      try {
        if (el.id == "webstore-purchase-disabled") {
          Event.stop(e);
        } else if (el.id == "webstore-purchase") {
          Event.stop(e);
          WebStore._purchaseConfirm(WebStore.selectedItem.id.split("-").last());
        } else if (el.id == "webstore-add") {
          Event.stop(e);
        } else if (el.id == "webstore-preview-bg") {
          Event.stop(e);
          
          if (!WebStore.backgroundPreviewWarning) {
            var warning = Dialog.createDialog("webstore-warning", "", 9003);
            Dialog.setAsWaitDialog(warning);
            Overlay.move(9002);
            Dialog.moveDialogToCenter(warning);
            Dialog.makeDialogDraggable(warning);
            new Ajax.Request(habboReqPath + "/myhabbo/store/background_warning", {
                method: "post",
                onComplete: function(req) {
                  if (WebStore._checkResponse(req.responseText)) {
                    Dialog.setDialogBody(warning, req.responseText);
                    WebStore.backgroundPreviewWarning = true;
                    
                    Event.observe($("webstore-warning-ok"), "click", function(e) {
                      Event.stop(e);
                      Element.remove(warning);
                      Overlay.move(9000);
                      WebStore._previewBg();
                    });
                  }
                }
              }
            );
          } else {
            WebStore._previewBg();
          }
        }
      } catch (ex) {}
    }
  }, 
  
  _previewBg: function() {
    var overlay = $("overlay");
    overlay.style.backgroundColor = "transparent";
    $("mypage-bg").className = WebStore.previewItems[WebStore.previewItemPointer].bgCssClass;
    window.setTimeout(function() {
      if (WebStore.dialog.style.left != "-1500px") {
        overlay.style.backgroundColor = "black";
      }
    }, 2500);
  }, 
  
  _handleTabClick: function(e) {
    Event.stop(e);
    if (!WebStore.loadingCategory && !WebStore.Inventory.loadingCategory) {
      var el = Event.findElement(e, "a");
      if (el && el.parentNode && el.parentNode.id) {
        if (el.parentNode.id != WebStore.selectedTab) {
          WebStore._selectTab(el.parentNode.id);
        }
      }
    }
  }, 
  
  _buildTabs: function() {
    WebStore.tabList = $("webstore-tabs");
    WebStore.tabList.appendChild(Builder.node("li", {id:"webstore-inventory"}, [
      Builder.node("a", {href:"#"}, [ document.createTextNode(WebStore.localization[0] + " "), Builder.node("span", {id:"webstore-inventory-new"}) ]),
      Builder.node("span", {className:"tab-spacer"})
    ]));
    WebStore.tabList.appendChild(Builder.node("li", {id:"webstore-store"}, [
      Builder.node("a", {href:"#"}, WebStore.localization[1]),
      Builder.node("span", {className:"tab-spacer"})
    ]));
  }, 
  
  _selectTab: function(id) {
    if (WebStore.selectedTab) { $(WebStore.selectedTab).className = ""; }
    $(id).className = "selected";
    WebStore.selectedTab = id;
    var header = $$("#purchase-main-dialog h3 span").first();
    
    if (id == "webstore-store") {
      if (header && WebStore.localization) { header.innerHTML = WebStore.localization[1]; }
      
      var type = "_webstore";
      if (WebStore.MenuBar.selectedMainCategory != null) {
        type = WebStore.MenuBar.selectedMainCategory.id.split("-").last();
      }
      WebStore.Inventory.hideInventoryDivs();
      
      WebStore._setDialogSize(type, function() {
        if (!WebStore.storeOpened) {
          WebStore._loadSubCategory(WebStore.selectedCategory, WebStore.selectedSubCategory);
          WebStore.storeOpened = true;
        }
        
        WebStore.showWebStoreDivs();
      });
    } else {
      if (header && WebStore.localization) { header.innerHTML = WebStore.localization[0]; }
      
      WebStore.hideWebStoreDivs();
      
      WebStore._setDialogSize("_inventory", function() {
        WebStore.Inventory.showInventoryDivs();
        
        if (!WebStore.Inventory.inventoryOpened) {
          WebStore.Inventory.loadCategory("stickers");
          WebStore.Inventory.inventoryOpened = true;
        }
        
        // reset counter
        WebStore.Inventory.newItems = [];
        WebStore.Inventory.updateNewItemCount();
        
        WebStore.Inventory.reloadIfNeeded();
      });
    }
  }, 
  
  _loadSubCategory: function(categoryId, subCategoryId, productId) {
    var query = { categoryId: categoryId };
    if (!!subCategoryId) {
      query.subCategoryId = subCategoryId;
    }
    new Ajax.Request(
      habboReqPath + "/myhabbo/store/items", {
        method: "post", parameters: query,
        onComplete: function(req, json) {
          if (WebStore._checkResponse(req.responseText)) {
            $("webstore-items").innerHTML = req.responseText;
            WebStore.selectedCategory = categoryId;
            WebStore.selectedSubCategory = subCategoryId;
            WebStore.loadingCategory = false;
            
            var itemSelected = false;
            var productEls = $A($("webstore-item-list").getElementsByTagName("li"));
            if (productId) {
              productEls.each(function(el) {
                if (el.id) {
                  var id = el.id.substring(el.id.lastIndexOf("-")+1);
                  if (id == productId) {
                    WebStore._selectItem(el);
                    WebStore._loadPreview(id);
                    itemSelected = true;
                    throw $break;
                  }
                }
              });
            }
            
            if (!itemSelected) {
              var el = productEls.first();
              if (el && el.id) {
                WebStore._selectItem(el);
                WebStore._loadPreview(el.id.substring(el.id.lastIndexOf("-")+1));
              }
            }
            
            // make sure inventory is hidden
            WebStore.Inventory.hideInventoryDivs();
          }
        }
      }
    );
  }, 
  
  _resetState: function() {
    WebStore.selectedItem = null;
    WebStore.selectedCategory = null;
    WebStore.selectedSubCategory = null;
    WebStore._showDefaultPreview();
    
  }, 
  
  _loadPreview: function(productId) {
    WebStore._clearPreview();
    Element.wait($("webstore-preview"));
    WebStore._showPreview();
    new Ajax.Request(
      habboReqPath + "/myhabbo/store/preview", {
        method: "post", parameters: { "productId": productId, "subCategoryId": WebStore.selectedSubCategory },
        onComplete: function(req, json) {
          if (WebStore._checkResponse(req.responseText)) {
            WebStore.previewItems = json;
            WebStore._showPreview(req.responseText);
            
            // make sure inventory is hidden
            WebStore.Inventory.hideInventoryDivs();
          }
        }
      }
    );
  }, 
  
  _showPreview: function(previewHtml) {
    var previewDiv = $("webstore-preview");
    if (previewHtml) { previewDiv.innerHTML = previewHtml };
    
    if (WebStore.previewItems) {
      var preview = $("webstore-preview-box").appendChild(Builder.node("div", { id: "webstore-preview-pre" }));
      
      if (WebStore.previewItems.length == 1 && WebStore.previewItems[0].itemCount > 1) {
        preview.appendChild(Builder.node("div", { id: "webstore-preview-count", className: "webstore-item-count" }));
      } else if (WebStore.previewItems.length > 1) {
        preview.appendChild(Builder.node("div", { id: "webstore-preview-count", className: "webstore-item-count" }));
        preview.appendChild(Builder.node("div", { id: "webstore-preview-page" }));
        Event.observe(preview.appendChild(Builder.node("div", { id: "webstore-preview-next" })), "click", function(e) { WebStore._nextPreviewItem(); });
        Event.observe(preview.appendChild(Builder.node("div", { id: "webstore-preview-prev" })), "click", function(e) { WebStore._previousPreviewItem(); });
      }
      
      WebStore._setPreviewItem();
    }
    
    previewDiv.show();
  }, 
  
  _showDefaultPreview: function() {
    WebStore._clearPreview();
    var previewDiv = $("webstore-preview");
    previewDiv.hide();
    $("webstore-preview-default").show();
    previewDiv.innerHTML = "";
  }, 
  
  _setContentClassName: function(className) {
    $("webstore-content-container").className = className;
  }, 
  
  _nextPreviewItem: function() {
    WebStore.previewItemPointer++;
    if (WebStore.previewItemPointer >= WebStore.previewItems.length) {
      WebStore.previewItemPointer = 0;
    }
    WebStore._setPreviewItem();
  }, 
  
  _previousPreviewItem: function() {
    WebStore.previewItemPointer--;
    if (WebStore.previewItemPointer < 0) {
      WebStore.previewItemPointer = WebStore.previewItems.length - 1;
    }
    WebStore._setPreviewItem();
  }, 
  
  _setPreviewItem: function() {
    var currItem = WebStore.previewItems[WebStore.previewItemPointer];
    var pre = $("webstore-preview-pre");
    pre.className = currItem.previewCssClass;
    pre.title = currItem.titleKey;
    if ($("webstore-preview-count")) {
      $("webstore-preview-count").innerHTML = "<div>x" + currItem.itemCount + "</div>";
    }
    if ($("webstore-preview-page")) {
      $("webstore-preview-page").innerHTML = (WebStore.previewItemPointer + 1) + "/" + WebStore.previewItems.length;
    }
    
    if (currItem.imageUrl && currItem.imageUrl != "") {
      pre.appendChild(Builder.node("div", { "class": "preview-image", "style": "background-image: url(" + habboImagerUrl + currItem.imageUrl +")"}));      
    }
    if (currItem.bgCssClass && currItem.bgCssClass != "") {
      if (!$("webstore-preview-bgpreview")) {
        pre.appendChild(Builder.node("div", { id: "webstore-preview-bgpreview" }, [ 
          Builder.node("a", { href:"#", className:"toolbutton", id: "webstore-preview-bg" }, [
            Builder.node("span", { id:"webstore-preview-bg-"+currItem.bgCssClass }, $("webstore-preview-bg-text").innerHTML)
          ])
        ]));
      }
    } else if ($("webstore-preview-bgpreview")) {
      Element.remove($("webstore-preview-bgpreview"));
    }
  }, 
    
  _clearPreview: function() {
    WebStore.previewItems = null;
    WebStore.previewItemPointer = 0;
    var previewBox = $("webstore-preview-box");
    if (previewBox) {
      previewBox.innerHTML = "";
    }
  }, 
  
  _purchaseConfirm: function(productId) {
    Overlay.move("9002");
    var dialog = Dialog.createDialog("webstore-confirm", "", "9003", -1500, 0, WebStore.closeConfirmation);
    Dialog.makeDialogDraggable(dialog);
    Dialog.setAsWaitDialog(dialog);
    Dialog.moveDialogToCenter(dialog);
    
    new Ajax.Request(habboReqPath + "/myhabbo/store/purchase_confirm", { 
      method: "post", parameters: { "productId" : productId, "subCategoryId" :  WebStore.selectedSubCategory }, 
      onComplete: function(req, json) {
        if (WebStore._checkResponse(req.responseText)) {
          Dialog.setDialogBody(dialog, req.responseText);
          if ($("webstore-confirm-submit")) {
            Event.observe($("webstore-confirm-submit"), "click", function(e) {
              Event.stop(e);
              WebStore._purchase(WebStore.selectedItem.id.split("-").last(), dialog);
            });
          }
          Event.observe($("webstore-confirm-cancel"), "click", WebStore.closeConfirmation);
        }
      }
    });
  }, 
  
  _purchase: function(productId, dialog) {
    Dialog.setAsWaitDialog(dialog);
    var type = WebStore.MenuBar.selectedMainCategory.id.split("-").last();
    new Ajax.Request(habboReqPath + "/myhabbo/store/purchase_" + type, { 
      method: "post", parameters: { task: "purchase", selectedId: productId }, onComplete: function(req, json) {
        if (WebStore._checkResponse(req.responseText)) {
          if (req.responseText.strip() != "OK") {
            Dialog.setDialogBody(dialog, req.responseText);
            Event.observe($("webstore-confirm-cancel"), "click", WebStore.closeConfirmation);
          } else {
            WebStore.Inventory.newItems.push(json);
            WebStore.Inventory.updateNewItemCount();
            Overlay.move("9000");
            Element.remove(dialog);
            
            // refresh both webstore and inventory (if needed)
            WebStore.openSubCategory(WebStore.selectedSubCategory, WebStore.selectedCategory, true, productId);
            if (WebStore.Inventory.inventoryOpened) {
              WebStore.Inventory.selectCategory(WebStore.MenuBar.selectedMainCategory.id.split("-").last());
              WebStore.Inventory._clearPreview();
              WebStore.Inventory.waitingForReload = true;
              WebStore.Inventory.lastId = json;
            }
          }
        }
      }
    });
  },
  
  _getCategoryOpener: function(mainCategoryType) {
    if (mainCategoryType == "avatar") {
      return function(subcategoryId, mainCategoryId, subcategoryEl) {
        if (!WebStore.loadingCategory) {
          WebStore.loadingCategory = true;
          WebStore._resetState();
          Element.wait($("webstore-items"));
          WebStore.StickerEditor.open();
        }
      };
    } else {
      return function(subcategoryId, mainCategoryId, subcategoryEl) {
        if (!WebStore.loadingCategory) {
          Element.wait($("webstore-items"));
          WebStore._resetState();
          WebStore.MenuBar.changeSubcategory(subcategoryId, mainCategoryId, subcategoryEl);
          WebStore.loadingCategory = true;
        }
      };
    }
  }, 
  
  _setDialogSize: function(mainCategoryType, afterFinishCallback) {
    if (mainCategoryType == "avatar") {
      new Effect.Transform([
        {"#purchase-main-dialog-body":"height: 399px"}, 
        {"#webstore-items":"height: 381px"}, 
        {"#webstore-categories":"height: 381px"}
      ], { afterFinish: afterFinishCallback || Prototype.emptyFunction }).play();
      $("webstore-close-container").style.display = "none";
    } else {
      new Effect.Transform([
        {"#purchase-main-dialog-body":"height: 324px"}, 
        {"#webstore-items":"height: 306px"}, 
        {"#webstore-categories":"height: 306px"}
      ], { afterFinish: afterFinishCallback || Prototype.emptyFunction }).play();
      $("webstore-close-container").style.display = "block";
    }
  }, 
  
  _checkResponse: function(responseText) {
    if (responseText.strip() == "REFRESH") {
      WebStore.NoteEditor.close();
      WebStore.close();
      Overlay.show();
      window.location.replace(window.location.href);
      return false;
    }
    return true;
  }
  
};

WebStore.MenuBar = {
  
  selectedMainCategory: null, 
  selectedSubcategory: null, 

    init: function(){
    WebStore.MenuBar._setEventHandler();
    
    WebStore.MenuBar.selectedMainCategory = $("webstore-categories").select(".webstore-selected-main").first();
    if (WebStore.MenuBar.selectedMainCategory) WebStore.selectedCategory = WebStore.MenuBar.selectedMainCategory.id.split("-")[1];
    WebStore.MenuBar.selectedSubcategory = $(WebStore.MenuBar.selectedMainCategory).select(".subcategory-selected").first();
    if (WebStore.MenuBar.selectedSubcategory) WebStore.selectedSubCategory = WebStore.MenuBar.selectedSubcategory.id.split("-")[2];
    },

    changeMainCategory: function(mainCategoryId, mainCategoryEl, mainCategoryType) {
      if (!WebStore.loadingCategory) {
      if (mainCategoryEl == WebStore.MenuBar.selectedMainCategory) {
          if (WebStore.MenuBar.selectedMainCategory != null) {
            if (Element.hasClassName(WebStore.MenuBar.selectedMainCategory, "selected-main-category")) {
            WebStore.MenuBar._closeMainCategory(mainCategoryId, mainCategoryEl);
            } else if (Element.hasClassName(WebStore.MenuBar.selectedMainCategory, "main-category")) {
              WebStore.MenuBar._setMainCategory(mainCategoryId, mainCategoryEl);
            }
          }
        return;
      }
      
      Overlay.show(); // re-display overlay if coming from avatar category
      
      var oldMainCat = WebStore.MenuBar.selectedMainCategory;
      var switched = false;
      if (oldMainCat != null && oldMainCat != mainCategoryEl) {
        WebStore.MenuBar._unselectSelectedMainCategory();
        WebStore.MenuBar._unselectSelectedSubcategory();
        switched = true;
      }
      WebStore.MenuBar._setMainCategory(mainCategoryId, mainCategoryEl);
      
      if (switched) {
        WebStore._showDefaultPreview();
        Element.wait($("webstore-items"));
        WebStore._setDialogSize(mainCategoryType, function() {
          var subcategoryEl = mainCategoryEl.select(".subcategory").first();
          if (subcategoryEl && subcategoryEl.id) {
            var subcategoryId = subcategoryEl.id.split("-")[2];
            WebStore._getCategoryOpener(mainCategoryType)(subcategoryId, mainCategoryId, subcategoryEl);
          } else {
            WebStore.selectedSubCategory = null;
            WebStore.MenuBar.selectedSubcategory = null;
          }
        });
      }
      }
  },
  
  changeSubcategory: function(subcategoryId, mainCategoryId, subcategoryEl) {
    WebStore.MenuBar._unselectSelectedSubcategory();
    WebStore.MenuBar.selectedSubcategory = subcategoryEl;
    subcategoryEl.className = "subcategory-selected";
    
    WebStore.openSubCategory(subcategoryId, mainCategoryId);
  }, 
  
  _setEventHandler: function() {
    $("webstore-categories").onclick = WebStore.MenuBar._handleCategoryClick.bindAsEventListener(this);
  }, 
  
  _handleCategoryClick: function(e) {
    Event.stop(e);
    if (!WebStore.loadingCategory) {
      var el = Event.findElement(e, "li");
      if (el && el.id) {
        if (el.id.indexOf("maincategory-") == 0) {
          var temp = el.id.split("-");
          WebStore.MenuBar.changeMainCategory(temp[1], el, temp[2]);
          WebStore._setContentClassName(temp[2]);
        } else if (el.id.indexOf("subcategory-") == 0) {
          var temp = el.id.split("-");
          WebStore.MenuBar.changeSubcategory(temp[2], temp[1], el);
          WebStore._setContentClassName(temp[3]);
        }
      }
    }
  }, 

    _setMainCategory: function(mainCategoryId, mainCategoryEl){
    mainCategoryEl.className = "selected-" + mainCategoryEl.className;
    if (mainCategoryEl.className.indexOf("no-subcategories") == -1) {
      Effect.SlideDown($("main-category-items-" + mainCategoryId), { duration: 0.2 });
    }
    WebStore.MenuBar.selectedMainCategory = mainCategoryEl;
    },

  _hideSubcategories: function(){
    $$(".purchase-subcategory-list").each(function (el) { Element.hide(el); });
  },
  
  _closeMainCategory: function(mainCategoryId, mainCategoryEl) {
    if (WebStore.MenuBar.selectedSubcategory != null && 
      Element.hasClassName(WebStore.MenuBar.selectedMainCategory, "selected-main-category")) {
      Effect.SlideUp("main-category-items-" + mainCategoryId, { duration: 0.2 });
    }
    if (WebStore.MenuBar.selectedSubcategory != mainCategoryEl) {
      WebStore.MenuBar._unselectSelectedMainCategory();
    }
  },
  
  _unselectSelectedSubcategory: function() {
    if (WebStore.MenuBar.selectedSubcategory) {
      WebStore.MenuBar.selectedSubcategory.className = "subcategory";
    }
  },
  
  _unselectSelectedMainCategory: function() {
    if (WebStore.MenuBar.selectedMainCategory != null) {
      if (Element.hasClassName(WebStore.MenuBar.selectedMainCategory, "selected-main-category-no-subcategories")) {
          WebStore.MenuBar.selectedMainCategory.className = "main-category-no-subcategories";
      } else if (Element.hasClassName(WebStore.MenuBar.selectedMainCategory, "selected-main-category")) {
          WebStore.MenuBar.selectedMainCategory.className = "main-category";
      }
    }
    WebStore.MenuBar._hideSubcategories();
  }
};

WebStore.Inventory = {
  
  newItems: [],
  inventoryOpened: false,
  selectedItem: null,
  selectedCategory: null, 
  loadingCategory: false, 
  previewItem: null, 
  waitingForReload: false,
  lastId: 0,
  
  updateNewItemCount: function() {
    var newDiv = $("webstore-inventory-new");
    if (newDiv) {
      var itemCount = WebStore.Inventory.newItems.length;
      if (itemCount > 0) {
        newDiv.innerHTML = " (" + itemCount + ")";
        newDiv.className = "new";
      } else {
        newDiv.innerHTML = "";
        newDiv.className = "";
      }
    }
  }, 
  
  load: function() {
    new Ajax.Request(
      habboReqPath + "/myhabbo/store/inventory", {
        method: "post", parameters: { type: "stickers" }, 
        onComplete: function(req, json) {
          if (WebStore._checkResponse(req.responseText)) {
            Dialog.setDialogBody(WebStore.dialog, req.responseText);
            WebStore.created = true;
            WebStore.Inventory.inventoryOpened = true;
            WebStore.MenuBar.init();
            WebStore.Inventory.initMenu();
            
            WebStore.localization = json[0];
            WebStore._buildTabs();
            WebStore._selectTab("webstore-inventory");
            
            WebStore.Inventory.previewItem = json[1];
            WebStore.Inventory._showPreview();
            
            var el = $A($("inventory-item-list").getElementsByTagName("li")).first();
            if (el) { WebStore.Inventory._selectItem(el); }
            
            WebStore._setEventHandlers();
          }
        }
      }
    );
  }, 
  
  initMenu: function() {
    $("inventory-categories").onclick = WebStore.Inventory._handleCategoryClick.bindAsEventListener(this);
    WebStore.Inventory.selectedCategory = $A($("inventory-categories").getElementsByTagName("li")).first().id.split("-").last();
  }, 
  
  loadCategory: function(type) {
    if (!WebStore.Inventory.loadingCategory) {
      Element.wait($("inventory-items"));
      WebStore.Inventory._clearPreview();
      WebStore.Inventory.loadingCategory = true;
      new Ajax.Request(
        habboReqPath + "/myhabbo/store/inventory_items", {
          method: "post", parameters: { type: type },
          onComplete: function(req, json) {
            if (WebStore._checkResponse(req.responseText)) {
              $("inventory-items").innerHTML = req.responseText;
              WebStore.Inventory.selectedCategory = type;
              WebStore.Inventory.loadingCategory = false;
              
              var items = $A($("inventory-item-list").getElementsByTagName("li"));
              var done = false;
              
              if (items && items.length > 0) {
                // check if the last purchased item is found and select that
                if (WebStore.Inventory.lastId != 0) {
                  items.each(function(el) {
                    if (el && el.id && (!el.className || !Element.hasClassName(el, "webstore-widget-disabled"))) {
                      var temp = el.id.split("-");
                      if (temp.last() == WebStore.Inventory.lastId) {
                        WebStore.Inventory._selectItem(el);
                        WebStore.Inventory._loadPreview(temp.last(), type, temp[2] == "p");
                        done = true;
                        WebStore.Inventory.lastId = 0;
                        throw $break;
                      }
                    }
                  });
                }
                
                // otherwise select the first available item
                if (!done) {
                  items.each(function(el) {
                    if (el && el.id && (!el.className || !Element.hasClassName(el, "webstore-widget-disabled"))) {
                      WebStore.Inventory._selectItem(el);
                      var temp = el.id.split("-");
                      WebStore.Inventory._loadPreview(temp.last(), type, temp[2] == "p");
                      done = true;
                      throw $break;
                    }
                  });
                }
              }

              if (!done) {
                WebStore.Inventory._showDefaultPreview();
              }
              
              // make sure webstore is hidden
              WebStore.hideWebStoreDivs();
            }
          }
        }
      );
    }
  }, 
  
  reloadIfNeeded: function() {
    if (WebStore.Inventory.waitingForReload && WebStore.Inventory.selectedCategory) {
      WebStore.Inventory.loadCategory(WebStore.Inventory.selectedCategory);
      WebStore.Inventory.waitingForReload = false;
    }
  }, 
  
  selectCategory: function(type, categoryEl) {
    if (!categoryEl) {
      categoryEl = $("inv-cat-" + type);
    }
    if (WebStore.Inventory.selectedCategory != type && categoryEl) {
      categoryEl.className = "selected-main-category-no-subcategories";
      if (WebStore.Inventory.selectedCategory != null) {
        $("inv-cat-" + WebStore.Inventory.selectedCategory).className = "main-category-no-subcategories";
      }
      WebStore.Inventory.selectedCategory = type;
      WebStore.Inventory.loadCategory(type);
      WebStore.Inventory._setContentClassName(type);
    }
  }, 
  
  showInventoryDivs: function() {
    WebStore.hideWebStoreDivs();
    $("inventory-categories-container").style.display = "block";
    $("inventory-content-container").style.display = "block";
    $("inventory-items-container").style.display = "block";
    $("inventory-preview-container").style.display = "block";
  }, 
  
  hideInventoryDivs: function() {
    $("inventory-categories-container").style.display = "none";
    $("inventory-content-container").style.display = "none";
    $("inventory-items-container").style.display = "none";
    $("inventory-preview-container").style.display = "none";
  }, 
  
  _handleCategoryClick: function(e) {
    Event.stop(e);
    if (!WebStore.Inventory.loadingCategory) {
      var el = Event.findElement(e, "li");
      if (el && el.id) {
        WebStore.Inventory.selectCategory(el.id.split("-").last(), el);
      }
    }
  }, 
  
  _setEventHandlers: function() {
    if ($("inventory-items")) {
      $("inventory-items").onclick = WebStore.Inventory._handleItemClick.bindAsEventListener(this);
    }
    
    if ($("inventory-preview")) {
      $("inventory-preview").onclick = WebStore.Inventory._handlePreviewClick.bindAsEventListener(this);
    }
  }, 
  
  _handleItemClick: function(e) {
    Event.stop(e);
    var el = Event.findElement(e, "li");
    if (el && el.id && el != WebStore.Inventory.selectedItem && (!el.className || !Element.hasClassName(el, "webstore-widget-disabled"))) {
      WebStore.Inventory._selectItem(el);
      var temp = el.id.split("-");
      WebStore.Inventory._loadPreview(temp.last(), WebStore.Inventory.selectedCategory, temp[2] == "p");
    }
  }, 
  
  _handlePreviewClick: function(e) {
    Event.stop(e);
    var el = Event.findElement(e, "a");
    if (el && el.id) {
      if (el.id == "inventory-place") {
        WebStore.Inventory._place();
      } else if (el.id == "inventory-place-all") {
        WebStore.Inventory._placeAll();
      }
    }
  }, 
  
  _setContentClassName: function(className) {
    $("inventory-content-container").className = className;
  },
  
  _selectItem: function(el) {
    try {
      if (!Element.hasClassName(el, "selected")) {
        Element.removeClassName(WebStore.Inventory.selectedItem, "selected");
        Element.addClassName(el, "selected");
        WebStore.Inventory.selectedItem = el;
      }
    } catch (ex) {}
  }, 
  
  _loadPreview: function(itemId, type, privileged) {
    WebStore.Inventory._clearPreview();
    Element.wait($("inventory-preview"));
    WebStore.Inventory._showPreview();
    var qs = { itemId: itemId, type: type };
    if (type == "widgets") {
      qs.privileged = privileged;
    }
    new Ajax.Request(
      habboReqPath + "/myhabbo/store/inventory_preview", {
        method: "post", parameters: qs,
        onComplete: function(req, json) {
          if (WebStore._checkResponse(req.responseText)) {
            WebStore.Inventory.previewItem = json;
            WebStore.Inventory._showPreview(req.responseText);
          }
          
          // make sure webstore is hidden
          WebStore.hideWebStoreDivs();
        }
      }
    );
  }, 
  
  _showPreview: function(previewHtml) {
    var previewDiv = $("inventory-preview");
    if (previewHtml) { previewDiv.innerHTML = previewHtml };
    
    if (WebStore.Inventory.previewItem && WebStore.Inventory.previewItem.length > 0) {
      var preview = $("inventory-preview-box").appendChild(Builder.node("div", { id: "inventory-preview-pre" }));
      if (WebStore.Inventory.previewItem[5] && WebStore.Inventory.previewItem[5] > 1) {
        preview.appendChild(Builder.node("div", { id: "inventory-preview-count", className: "webstore-item-count" }));
      }
      
      WebStore.Inventory._setPreviewItem();
    }
    
    previewDiv.show();
  }, 
    
  _clearPreview: function() {
    WebStore.Inventory.previewItem = null;
    var previewBox = $("inventory-preview");
    if (previewBox) {
      previewBox.innerHTML = "";
    }
  }, 
  
  _showDefaultPreview: function() {
    WebStore.Inventory._clearPreview();
    var previewDiv = $("inventory-preview");
    previewDiv.hide();
    $("inventory-preview-default").show();
    previewDiv.innerHTML = "";
  }, 
  
  _setPreviewItem: function() {
    var pre = $("inventory-preview-pre");
    pre.className = WebStore.Inventory.previewItem[0];
    pre.title = WebStore.Inventory.previewItem[2];
    if (WebStore.Inventory.previewItem[3] == "DynamicSticker") {
      pre.style.backgroundImage = "url(" + habboImagerUrl + WebStore.Inventory.previewItem[4] + ")";
    }
    if ($("inventory-preview-count")) {
      $("inventory-preview-count").innerHTML = "<div>x" + WebStore.Inventory.previewItem[5] + "</div>";
    }
  }, 
  
  _place: function() {
    if (WebStore.Inventory.selectedCategory == "stickers") {
      doPlaceImageOnPage(WebStore.Inventory.selectedItem.id.split("-").last());
      WebStore.Inventory._closeAfterPlace();
    } else if (WebStore.Inventory.selectedCategory == "backgrounds") {
      WebStore.originalBg = WebStore.Inventory.previewItem[1];
      doChangeBg(WebStore.Inventory.previewItem[1], WebStore.Inventory.selectedItem.id.split("-").last());
      WebStore.Inventory._closeAfterPlace();
    } else if (WebStore.Inventory.selectedCategory == "widgets") {
      var temp = WebStore.Inventory.selectedItem.id.split("-");
      doPlaceWidget(temp[3], temp[2] == "p");
      WebStore.Inventory._closeAfterPlace();
    } else if (WebStore.Inventory.selectedCategory == "notes") {
      WebStore.NoteEditor.open();
    }
  }, 
  
  _placeAll: function() {
    if (WebStore.Inventory.selectedCategory == "stickers") {
      doPlaceImageOnPage(WebStore.Inventory.selectedItem.id.split("-").last(), true);
      WebStore.Inventory._closeAfterPlace();
    }
  }, 
  
  _closeAfterPlace: function() {
    WebStore.close();
    WebStore.Inventory._clearPreview();
    Element.wait($("inventory-items"));
    WebStore.Inventory.waitingForReload = true;
  }
  
}

WebStore.StickerEditor = {
  
  figureParams: null, 
  
  open: function() {
    new Ajax.Request(
      habboReqPath + "/myhabbo/stickereditor/stickereditor_flash", {
        method: "post", parameters: WebStore.StickerEditor.figureParams || "", 
        onComplete: function(req, json) {
          if (WebStore._checkResponse(req.responseText)) {
            Overlay.hideIfMacFirefox();
            
            var itemsDiv = $("webstore-items");
            itemsDiv.innerHTML = req.responseText;
            req.responseText.evalScripts();
            
            WebStore.loadingCategory = false;
          }
        }
      }
    );
  }, 
  
  preview: function(figureParams) {
    WebStore.StickerEditor.figureParams = figureParams;
    
    $("flashcontent").style.display = "none";
    $("flashalternate").style.display = "block";
    
    Overlay.show();
    Overlay.move("9002");
    var dialog = Dialog.createDialog("webstore-confirm", "", "9003", -1500, 0, function(e) {
      WebStore.closeConfirmation(e);
      WebStore.StickerEditor.open();
    });
    Dialog.makeDialogDraggable(dialog);
    Dialog.setAsWaitDialog(dialog);
    Dialog.moveDialogToCenter(dialog);
    
    new Ajax.Request(habboReqPath + "/myhabbo/stickereditor/preview", {
      method: "post", parameters: figureParams || "", 
      onComplete: function(req, json) {
        if (WebStore._checkResponse(req.responseText)) {
          Dialog.setDialogBody(dialog, req.responseText);
          Event.observe($("avatarimage-preview-img"), "load", function(e) { Element.remove("avatarimage-progressbar"); });
          Event.observe($("avatarimage-preview-purchase"), "click", function(e) {
            Event.stop(e);
            Dialog.setAsWaitDialog(dialog);
            WebStore.StickerEditor._purchase(json);
          });
          Event.observe($("avatarimage-preview-edit"), "click", function(e) {
            Event.stop(e);
            Element.remove($("webstore-confirm"));
            Overlay.move("9000");
            Overlay.hideIfMacFirefox();
            Element.wait($("flashcontent"));
            $("flashalternate").style.display = "none";
            $("flashcontent").style.display = "block";
            WebStore.StickerEditor.open();
          });
        }
      }
    });
  }, 
  
  _purchase: function(imageUrl) {
    new Ajax.Request(habboReqPath + "/myhabbo/store/purchase_avatarsticker", {
      method: "post", parameters: { stickerimage: imageUrl },
      onComplete: function(req, json) {
        if (WebStore._checkResponse(req.responseText)) {
          if (req.responseText.strip() != "OK") {
            Dialog.setDialogBody($("webstore-confirm"), req.responseText);
            Event.observe($("webstore-confirm-cancel"), "click", WebStore.closeConfirmation);
          } else {
            WebStore.Inventory.newItems.push(json);
            WebStore.Inventory.updateNewItemCount();
            Element.remove($("webstore-confirm"));
            WebStore.StickerEditor.open();
            Overlay.move("9000");
            
            // refresh inventory (if needed)
            if (WebStore.Inventory.inventoryOpened) {
              WebStore.Inventory.selectCategory(WebStore.MenuBar.selectedMainCategory.id.split("-").last());
              WebStore.Inventory._clearPreview();
              WebStore.Inventory.waitingForReload = true;
              WebStore.Inventory.lastId = json;
            }
          }
        }
      }
    });
  }
  
}

WebStore.NoteEditor = {
  
  dialog: null, 
  noteParams: null, 
  id: null, 
  
  open: function() {
    Overlay.move("9002");
    WebStore.NoteEditor._createEditorDialog();
    WebStore.NoteEditor._loadEditor();
  }, 
  
  close: function(e) {
    if (e) { Event.stop(e); }
    if ($("webstore-noteeditor")) {
      Element.remove($("webstore-noteeditor"));
      if (WebStore.NoteEditor.id) {
        Overlay.hide();
      } else {
        Overlay.move("9000");
      }
    }
    WebStore.NoteEditor.id = null;
  }, 
  
  edit: function(id) {
    WebStore.NoteEditor.id = id;
    Overlay.show();
    WebStore.NoteEditor._createEditorDialog();
    WebStore.NoteEditor._loadEditor({ id: id });
  }, 
  
  _createEditorDialog: function() {
    WebStore.NoteEditor.dialog = Dialog.createDialog("webstore-noteeditor", "", "9003", -1500, 0, WebStore.NoteEditor.close);
    Dialog.makeDialogDraggable(WebStore.NoteEditor.dialog);
    Dialog.setAsWaitDialog(WebStore.NoteEditor.dialog);
    Dialog.moveDialogToCenter(WebStore.NoteEditor.dialog);
  }, 
  
  _loadEditor: function(noteParams, backFromPreview) {
    WebStore.NoteEditor.noteParams = noteParams || "";
    
    new Ajax.Request(habboReqPath + "/myhabbo/noteeditor/editor", {
      method: "post", parameters: WebStore.NoteEditor.noteParams, onComplete: function(req, json) {
            if (WebStore._checkResponse(req.responseText)) {
              Dialog.setDialogBody(WebStore.NoteEditor.dialog, req.responseText);
              req.responseText.evalScripts();

              var limitCallback = function(limitReached) {
                try {
                  var currentLength = $F("webstore-notes-text").length;
                  $("webstore-notes-counter").innerHTML = maxLength - currentLength;
                  if (currentLength > 0) { WebStore.NoteEditor._enableContinue(); }
                  else { WebStore.NoteEditor._disableContinue(); }
                } catch (e) {}
              };
            
              if (backFromPreview || WebStore.NoteEditor.id) {
                window.setTimeout(function() {
                  $("webstore-notes-text").focus();
                  limitCallback();
                }, 100);
              }
              
              var maxLength = $F("webstore-notes-maxlength");
              Utils.limitTextarea($("webstore-notes-text"), maxLength, limitCallback);
              if (maxLength < 0) {
                  WebStore.NoteEditor._enableContinue();
              }
              
              if ($("webstore-confirm-cancel")) { Event.observe($("webstore-confirm-cancel"), "click", WebStore.NoteEditor.close); }
              if ($("webstore-notes-continue")) { Event.observe($("webstore-notes-continue"), "click", function(e) {
                Event.stop(e);
                var el = Event.findElement(e, "a");
                
                if (!el.className || !Element.hasClassName(el, "disabled-button")) {
                  WebStore.NoteEditor.noteParams = Form.serialize($("webstore-notes-form"));
                  Dialog.setAsWaitDialog(WebStore.NoteEditor.dialog);
                  new Ajax.Request(habboReqPath + "/myhabbo/noteeditor/preview", {
                    method: "post", parameters: WebStore.NoteEditor.noteParams, onComplete: function(req, json) {
                      if (WebStore._checkResponse(req.responseText)) {
                        if (req.responseText.strip() == "BACK") {
                          WebStore.NoteEditor._loadEditor(WebStore.NoteEditor.noteParams, true);
                        } else {
                          Dialog.setDialogBody(WebStore.NoteEditor.dialog, req.responseText);
                          
                          if ($("webstore-confirm-cancel")) { Event.observe($("webstore-confirm-cancel"), "click", WebStore.NoteEditor.close); }
                          if ($("webstore-notes-add")) { Event.observe($("webstore-notes-add"), "click", function(e) {
                            var isEditing = !!WebStore.NoteEditor.id;
                            WebStore.NoteEditor.close(e);
                            if (isEditing) {
                              WebStore.NoteEditor._saveChanges();
                            } else {
                              WebStore.NoteEditor._place();
                              WebStore.Inventory._closeAfterPlace();
                            }
                          }); }
                          if ($("webstore-notes-edit")) { Event.observe($("webstore-notes-edit"), "click", function(e) {
                            Event.stop(e);
                            WebStore.NoteEditor._loadEditor(WebStore.NoteEditor.noteParams, true);
                          });
                        }
                      }
                    }
                  }
                });
              }
            });
          }
        }
      }
    });
  }, 
  
  _place: function() {
    if (WebStore.NoteEditor.noteParams) {
      new Ajax.Request(habboReqPath + "/myhabbo/noteeditor/place", {
        method: "post", parameters: WebStore.NoteEditor.noteParams, onComplete: function(req, json) {
          if (WebStore._checkResponse(req.responseText)) {
            if (req.responseText.strip() == "BACK") {
              WebStore.NoteEditor._loadEditor(WebStore.NoteEditor.noteParams, true);
            } else if (req.responseText.strip() != "") {
              $("playground").insert(req.responseText);
              var note = $("stickie-" + json);
              Element.hide(note);
              note.style.top = "10px";
              note.style.left = "10px";
              Effect.BlindDown(note, {scaleX:true, scaleY:true});
              initMovableItems();
              Utils.setAllEmbededObjectsVisibility('hidden');
            }
          }
        }
      });
    }
  }, 
  
  _saveChanges: function() {
    if (WebStore.NoteEditor.noteParams) {
      new Ajax.Request(habboReqPath + "/myhabbo/noteeditor/save", {
        method: "post", parameters: WebStore.NoteEditor.noteParams, onComplete: function(req, json) {
          if (WebStore._checkResponse(req.responseText)) {
            if (req.responseText.strip() == "BACK") {
              WebStore.NoteEditor._loadEditor(WebStore.NoteEditor.noteParams, true);
            } else if (req.responseText.strip() != "") {
              var oldNote = $("stickie-" + json);
              var x = oldNote.offsetLeft;
              var y = oldNote.offsetTop;
              Element.remove(oldNote);
              $("playground").insert(req.responseText);
              var note = $("stickie-" + json);
              Element.hide(note);
              note.style.top = y+"px";
              note.style.left = x+"px";
              Effect.Appear(note);
              initMovableItems();
              Utils.setAllEmbededObjectsVisibility('hidden');
            }
          }
        }
      });
    }
  }, 
  
  _enableContinue: function() {
    Element.removeClassName($("webstore-notes-continue"), "disabled-button");
  }, 
  
  _disableContinue: function() {
    Element.addClassName($("webstore-notes-continue"), "disabled-button");
  }
  
}

ScriptLoader.notify("myhabbo-store");