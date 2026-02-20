var ListPaging = Class.create();
ListPaging.prototype = {
    setOptions: function(A) {
        this.options = this.options || {};
        Object.extend(this.options, A || {})
    },
    initialize: function() {
        this.pageNumber = 1;
        this.bindElementsAndEvents()
    },
    bindElementsAndEvents: function() {
        this.contentElement = $(this.options.contentElementId);
        this.pagingElement = $(this.options.pagingElementId);
        if (this.pagingElement) {
            this.pagingElement.onclick = this._processClick.bindAsEventListener(this)
        }
    },
    _processClick: function(B) {
        var A = Event.element(B);
        if (A.nodeName.toLowerCase() == "a" && A.className == this.options.pagingListLinkClass) {
            Event.stop(B);
            this._findPage(B)
        }
    },
    _findPage: function(E) {
        var B = Event.element(E);
        var A = parseInt($F("pageNumber"));
        var D = parseInt($F("totalPages"));
        var C = 1;
        if (B.id == "list-first") {
            C = 1
        } else {
            if (B.id == "list-previous") {
                C = A - 1
            } else {
                if (B.id == "list-next") {
                    C = A + 1
                } else {
                    if (B.id == "list-last") {
                        C = D
                    }
                }
            }
        }
        this._processSearch(C)
    },
    _processSearch: function(A) {
        new Ajax.Updater(this.contentElement, habboReqPath + this.options.searchUrl, {
            method: "post",
            parameters: "pageNumber=" + encodeURIComponent(A),
            onComplete: function() {
                this.bindElementsAndEvents()
            }.bind(this)
        })
    }
};
var QuickMenuListPaging = Class.create(ListPaging, {
    initialize: function($super, A, B) {
        this.setOptions({
            searchUrl: B,
            contentElementId: "qtab-container-" + A,
            pagingElementId: "qtab-" + A + "-list-paging",
            pagingListLinkClass: "qtab-" + A + "-list-paging-link"
        });
        $super()
    }
});
var HabbletPaging = Class.create(ListPaging, {
    initialize: function($super, A) {
        this.setOptions({
            searchUrl: A
        });
        $super()
    },
    _findPage: function($super, E) {
        var B = Event.element(E);
        var A = parseInt($F(this.options.contentElementId + "-pageNumber"));
        var D = parseInt($F(this.options.contentElementId + "-totalPages"));
        var C = 1;
        if (B.id == this.options.contentElementId + "-list-first") {
            C = 1
        } else {
            if (B.id == this.options.contentElementId + "-list-previous") {
                C = A - 1
            } else {
                if (B.id == this.options.contentElementId + "-list-next") {
                    C = A + 1
                } else {
                    if (B.id == this.options.contentElementId + "-list-last") {
                        C = D
                    } else {
                        if (B.id.indexOf(this.options.contentElementId + "-list-page-") != -1) {
                            C = B.id.substring(this.options.contentElementId.length + 11)
                        }
                    }
                }
            }
        }
        this._processSearch(C)
    }
});
var HabbletGroupPaging = Class.create(HabbletPaging, {
    initialize: function($super, A) {
        this.setOptions({
            contentElementId: "groups-habblet-list-container",
            pagingElementId: "groups-habblet-list-container-list-paging",
            pagingListLinkClass: "groups-habblet-list-container-list-paging-link"
        });
        $super(A)
    }
});
var HabbletFriendsPaging = Class.create(HabbletPaging, {
    initialize: function($super, A) {
        this.setOptions({
            contentElementId: "friends-habblet-list-container",
            pagingElementId: "friends-habblet-list-container-list-paging",
            pagingListLinkClass: "friends-habblet-list-container-list-paging-link"
        });
        $super(A)
    }
});
var HabbletSearchPaging = Class.create(HabbletPaging, {
    initialize: function($super, A) {
        this.setOptions({
            contentElementId: "avatar-habblet-list-container",
            pagingElementId: "avatar-habblet-list-container-list-paging",
            pagingListLinkClass: "avatar-habblet-list-container-list-paging-link"
        });
        $super(A)
    },
    _processSearch: function($super, B) {
        var A = $F("avatar-habblet-search-string");
        new Ajax.Updater(this.contentElement, this.options.searchUrl, {
            method: "post",
            parameters: "pageNumber=" + encodeURIComponent(B) + "&searchString=" + encodeURIComponent(A),
            onComplete: function() {
                this.bindElementsAndEvents()
            }.bind(this)
        })
    }
});
var HabboSearchHabblet = Class.create();
HabboSearchHabblet.prototype = {
    minSearchLength: 0,
    maxSearchLength: 0,
    initialize: function(B, A) {
        this.maxSearchLength = A;
        this.minSearchLength = B;
        this.habbletPaging = new HabbletSearchPaging(habboReqPath + "/habblet/habbosearchcontent");
        Event.observe("avatar-habblet-search-button", "click", function(C) {
            Event.stop(C);
            this._processSearch(C)
        }.bind(this));
        Event.observe("avatar-habblet-search-string", "keypress", function(C) {
            if (C.keyCode == Event.KEY_RETURN) {
                this._processSearch(C)
            }
        }.bind(this));
        $("avatar-habblet-content").observe("click", Event.delegate({
            "a.add": function(E) {
                E.stop();
                var D = E.element();
                var G = D.readAttribute("avatarid");
                if (!!D && !!G) {
                    var F = function() {
                        Overlay.hide();
                        $("avatar-habblet-dialog").remove()
                    };
                    var C = Dialog.createDialog("avatar-habblet-dialog", L10N.get("habblet.search.add_friend.title"), 9001, 0, -1000, F);
                    $("avatar-habblet-dialog").observe("click", Event.delegate({
                        "a.done > *": function(H) {
                            H.stop();
                            F()
                        },
                        "a.add-continue > *": function(H) {
                            H.stop();
                            Dialog.setAsWaitDialog(C);
                            new Ajax.Request(habboReqPath + "/habblet/ajax/addFriend", {
                                parameters: {
                                    accountId: G
                                },
                                onComplete: function(I) {
                                    D.hide();
                                    Dialog.setDialogBody(C, I.responseText)
                                }
                            })
                        }
                    }));
                    Dialog.setAsWaitDialog(C);
                    Dialog.moveDialogToCenter(C);
                    Overlay.show();
                    new Ajax.Request(habboReqPath + "/habblet/ajax/confirmAddFriend", {
                        parameters: {
                            accountId: G
                        },
                        onComplete: function(H) {
                            Dialog.setDialogBody(C, H.responseText)
                        }
                    })
                }
            },
            "ul *:not(a)": function(D) {
                var C = D.findElement("li");
                if (!!C.readAttribute("homeurl")) {
                    location.href = C.readAttribute("homeurl")
                }
            }
        }));
        Utils.limitTextarea("avatar-habblet-search-string", this.maxSearchLength, function(C) {
            var D = $("habbo-search-error-container");
            if (C && !Element.visible(D)) {
                $("habbo-search-error").innerHTML = L10N.get("habblet.search.error.search_string_too_long");
                Element.show(D)
            } else {
                if (!C) {
                    Element.hide(D)
                }
            }
        })
    },
    _processSearch: function(B) {
        var A = $F("avatar-habblet-search-string");
        A = A.strip();
        if (this._isValidSearchString(A)) {
            Element.wait($("avatar-habblet-list-container"));
            new Ajax.Updater("avatar-habblet-list-container", habboReqPath + "/habblet/habbosearchcontent", {
                method: "post",
                parameters: "searchString=" + encodeURIComponent(A),
                onComplete: function() {
                    this.habbletPaging.bindElementsAndEvents()
                }.bind(this)
            })
        } else {
            Element.show($("habbo-search-error-container"))
        }
    },
    _isValidSearchString: function(A) {
        if (A.length < this.minSearchLength) {
            $("habbo-search-error").innerHTML = L10N.get("habblet.search.error.search_string_too_short");
            return false
        } else {
            if (A.length > this.maxSearchLength) {
                $("habbo-search-error").innerHTML = L10N.get("habblet.search.error.search_string_too_long")
            }
        }
        return true
    }
};
var HighscoreHabblet = Class.create();
HighscoreHabblet.prototype = {
    initialize: function(A) {
        this.habbletId = A;
        this.containerEl = $("highscores-habblet-list-container-" + A);
        this._setupPaging();
        this._setupGameLinks()
    },
    _setupPaging: function() {
        if ($("habblet-paging-" + this.habbletId)) {
            Event.observe($("habblet-paging-" + this.habbletId), "click", function(A) {
                Event.stop(A);
                this._handlePagingClick(A)
            }.bind(this))
        }
    },
    _handlePagingClick: function(D) {
        var C = Event.findElement(D, "a");
        if (Element.hasClassName(C, "list-paging-link")) {
            var A = $F(this.habbletId + "-pageNumber");
            var B = $F(this.habbletId + "-gameId");
            switch (C.id.split("-").last()) {
                case "next":
                    A++;
                    break;
                case "previous":
                    A--;
                    break
            }
            this._updateContent(A, B)
        }
    },
    _setupGameLinks: function() {
        Event.observe($("highscores-habblet-games-" + this.habbletId), "click", function(A) {
            Event.stop(A);
            this._handleGameLinkClick(A)
        }.bind(this))
    },
    _handleGameLinkClick: function(C) {
        var B = Event.findElement(C, "a");
        if (Element.hasClassName(B, "highscores-habblet-game-link")) {
            var A = B.id.split("-").last();
            this._updateContent(0, A)
        }
    },
    _updateContent: function(A, B) {
        new Ajax.Updater(this.containerEl, habboReqPath + "/habblet/personalhighscores", {
            method: "post",
            parameters: {
                pageNumber: A,
                gameId: B,
                hid: this.habbletId
            },
            onComplete: function() {
                this._setupPaging();
                this._setupGameLinks()
            }.bind(this)
        })
    }
};
var InviteFriendHabblet = Class.create();
InviteFriendHabblet.prototype = {
    initialize: function(B) {
        Event.observe("send-friend-invite-button", "click", function(C) {
            Event.stop(C);
            this._sendInvite()
        }.bind(this));
        Event.observe("getlink-friend-invite-button", "click", function(C) {
            Event.stop(C);
            this._getInviteLink()
        }.bind(this));
        Utils.limitTextarea("invitation_message", B, function(C) {
            var E = $("invitation_message_error");
            if (C && !Element.visible(E)) {
                var D = $$("#invitation_message_error .rounded").first();
                D.innerHTML = L10N.get("invitation.error.message_too_long");
                Element.show(E)
            } else {
                if (!C) {
                    Element.hide(E)
                }
            }
        });
        for (var A = 1; A < 4; A++) {
            Event.observe($("invitation_recipient" + A), "focus", function(D) {
                var C = Event.element(D);
                if (C && C.value == L10N.get("invitation.form.recipient")) {
                    C.value = ""
                }
            });
            Event.observe($("invitation_recipient" + A), "blur", function(D) {
                var C = Event.element(D);
                if (C && C.value == "") {
                    C.value = L10N.get("invitation.form.recipient")
                }
            })
        }
    },
    _sendInvite: function() {
        var B = encodeURIComponent($("invitation_message").value);
        for (var A = 1; A < 4; A++) {
            if ($("invitation_recipient" + A).value != L10N.get("invitation.form.recipient")) {
                B += "&recipientEmails=" + encodeURIComponent($("invitation_recipient" + A).value)
            }
        }
        new Ajax.Updater("friend-invitation-habblet-container", habboReqPath + "/habblet/ajax/mgmsendinvite", {
            method: "post",
            parameters: "message=" + B,
            evalScripts: true,
            onComplete: function(D, E) {
                if (E == "success") {
                    for (var C = 1; C < 4; C++) {
                        $("invitation_recipient" + C).value = L10N.get("invitation.form.recipient")
                    }
                }
            }
        })
    },
    _getInviteLink: function() {
        $("invitation-link-container").wait(75);
        new Ajax.Updater("invitation-link-container", habboReqPath + "/habblet/ajax/mgmgetinvitelink", {
            method: "post",
            evalScripts: true,
            onComplete: function(A, B) {}
        })
    }
};
var PurseHabblet = Class.create();
PurseHabblet.prototype = {
    busy: false,
    initialize: function() {
        Event.observe("voucher-form", "submit", this._redeemVoucher.bind(this));
        var A = $("purse-redeemcode-button");
        if (A) {
            A.observe("click", this._redeemVoucher.bind(this));
            document.observe("dom:loaded", function() {
                $("purse-habblet-redeemcode-string").setStyle({
                    width: ($(A.parentNode).getWidth() - A.getWidth() - 50) + "px"
                })
            })
        }
    },
    _redeemVoucher: function(A) {
        if (this.busy) {
            return
        }
        this.busy = true;
        Event.stop(A);
        new Ajax.Request(habboReqPath + "/habblet/ajax/redeemvoucher", {
            method: "post",
            parameters: {
                voucherCode: $F("purse-habblet-redeemcode-string")
            },
            onComplete: function(C) {
                var B = $("voucher-form");
                B.innerHTML = C.responseText;
                B.select(".rounded").each(function(D) {
                    Rounder.addCorners(D, 8, 8)
                });
                if ($("purse-redeemcode-button")) {
                    Event.observe("purse-redeemcode-button", "click", this._redeemVoucher.bind(this))
                }
                this.busy = false
            }.bind(this)
        })
    }
};
var ActiveHabbosHabblet = Class.create();
ActiveHabbosHabblet.prototype = {
    numberOfRows: 3,
    numberOfColumns: 6,
    horizontalSpace: 62,
    verticalSpace: 45,
    numberOfImages: 18,
    initialize: function() {
        this._positionPlaceHolderImages()
    },
    generateRandomImages: function() {
        var C = $("homes-habblet-list-container").select(".active-habbo-image");
        var E = [];
        var A = 0;
        while (E.length < C.length) {
            var B = Math.floor(Math.random() * C.length);
            var D = C[B];
            var F = $("active-habbo-image-placeholder-" + A);
            if (E.indexOf(B) == -1) {
                $("imagemap-area-" + A).href = $("active-habbo-url-" + B).value;
                this._addToolTip(A, $("active-habbo-data-" + B));
                this._placeImage(F, D);
                E.push(B);
                A++
            }
            if (A == this.numberOfImages) {
                break
            }
        }
    },
    _placeImage: function(B, A) {
        window.setTimeout(function() {
            B.style.backgroundImage = "url(" + A.value + ")"
        }, Math.floor(Math.random() * 700))
    },
    _addToolTip: function(A, B) {
        new Tip("imagemap-area-" + A, B.innerHTML, {
            className: "bubbletip",
            title: " ",
            target: $("active-habbo-image-placeholder-" + A),
            hook: {
                target: "topRight",
                tip: "bottomRight"
            },
            offset: {
                x: 85,
                y: 40
            }
        })
    },
    _positionPlaceHolderImages: function() {
        var C = $("homes-habblet-list-container").select(".active-habbo-image-placeholder");
        var E = 10;
        var D = 50;
        var B = 0;
        for (var G = 0; G < this.numberOfRows; G++) {
            for (var A = 0; A < this.numberOfColumns; A++) {
                var F = C[B];
                if (F) {
                    F.style.left = D + "px";
                    F.style.top = E + "px";
                    D = D + this.horizontalSpace;
                    B = B + 1
                }
            }
            if (G % 2 < 1) {
                D = 20
            } else {
                D = 50
            }
            E = E + this.verticalSpace
        }
        C.each(function(H) {
            H.style.display = "block"
        })
    }
};
var RoomSelectionHabblet = {
    initClosableHabblet: function() {
        $("habblet-close-roomselection").observe("click", function(A) {
            RoomSelectionHabblet.showConfirmation()
        })
    },
    hideHabblet: function() {
        new Ajax.Request(habboReqPath + "/habblet/ajax/roomselectionHide");
        Effect.Fade("roomselection", {
            afterFinish: function() {
                $("roomselection").remove()
            }
        })
    },
    showConfirmation: function() {
        Overlay.show();
        var A = Dialog.createDialog("roomselection-dialog", L10N.get("roomselection.hide.title"), false, false, false, RoomSelectionHabblet.hideConfirmation);
        Dialog.setAsWaitDialog(A);
        Dialog.makeDialogDraggable(A);
        Dialog.moveDialogToCenter(A);
        new Ajax.Request(habboReqPath + "/habblet/ajax/roomselectionConfirm", {
            onComplete: function(B) {
                $("roomselection-dialog-body").update(B.responseText);
                $("roomselection-cancel").observe("click", function(C) {
                    Event.stop(C);
                    RoomSelectionHabblet.hideConfirmation()
                });
                if (!!$("roomselection-hide")) {
                    $("roomselection-hide").observe("click", function(C) {
                        Event.stop(C);
                        RoomSelectionHabblet.hideConfirmation();
                        RoomSelectionHabblet.hideHabblet()
                    })
                }
            }
        })
    },
    hideConfirmation: function() {
        $("roomselection-dialog").remove();
        Overlay.hide()
    },
    create: function(B, D) {
        var A = false;
        try {
            A = window.habboClient
        } catch (C) {}
        if (A) {
            window.location.href = B;
            return false
        }
        if (document.habboLoggedIn) {
            new Ajax.Request(habboReqPath + "/habblet/ajax/roomselectionCreate", {
                parameters: {
                    roomType: D
                }
            })
        }
        HabboClient.openOrFocus(B);
        if ($("roomselection-plp-intro")) {
            $("roomselection-plp", "habblet-close-roomselection").invoke("hide");
            $("roomselection-plp-intro").update(L10N.get("roomselection.old_user.done"))
        }
        return false
    }
};
var GiftQueueHabblet = {
    init: function(A) {
        GiftQueueHabblet.container = $("gift-countdown");
        if (!!GiftQueueHabblet.container) {
            new PrettyTimer(A, function(B) {
                GiftQueueHabblet.container.update(B)
            }, {
                showDays: false,
                showMeaningfulOnly: false,
                localizations: {
                    hours: L10N.get("time.hours") + " ",
                    minutes: L10N.get("time.minutes") + " ",
                    seconds: L10N.get("time.seconds")
                },
                endCallback: function() {
                    GiftQueueHabblet.reload()
                }
            })
        }
    },
    initClosableHabblet: function() {
        $("habblet-close-giftqueue").setStyle({
            display: "inline"
        });
        $("habblet-close-giftqueue").observe("click", function(A) {
            GiftQueueHabblet.hide()
        })
    },
    reload: function() {
        new Ajax.Request(habboReqPath + "/habblet/ajax/nextgift", {
            onComplete: function(B, A) {
                $("gift-container").update(B.responseText);
                GiftQueueHabblet.init(parseInt(A))
            }
        })
    },
    hide: function() {
        new Ajax.Request(habboReqPath + "/habblet/ajax/giftqueueHide");
        Effect.Fade("giftqueue", {
            afterFinish: function() {
                $("giftqueue").remove()
            }
        })
    }
};
var CurrentRoomEvents = {
    init: function() {
        $("event-category").observe("change", function(A) {
            Element.wait($("event-list"));
            new Ajax.Updater("event-list", habboReqPath + "/habblet/ajax/load_events", {
                parameters: {
                    eventTypeId: $F("event-category")
                },
                onComplete: function() {
                    CurrentRoomEvents.initListItems()
                }
            })
        });
        CurrentRoomEvents.initListItems()
    },
    initListItems: function() {
        $$("#current-events ul.habblet-list").each(function(A) {
            Event.observe(A, "click", function(C) {
                var D = Event.element(C);
                if (D.tagName.toUpperCase() == "A") {
                    return
                }
                Event.stop(C);
                if (!$(D).match("li")) {
                    D = $(D).up("li")
                }
                var B = $(D).readAttribute("roomid");
                if (B) {
                    HabboClient.roomForward(habboReqPath + "/client?forwardId=2&roomId=" + B, B, "private")
                }
            })
        })
    }
};
var TaggingGameHabblet = Class.create();
TaggingGameHabblet.prototype = {
    currentCategoryShown: 0,
    categoriesCount: 0,
    onWeb: true,
    initialize: function(B) {
        var A = this;
        A.onWeb = B;
        if (!B) {
            $("tagging-game").hide();
            $("show-client").setStyle({
                display: "none"
            });
            Event.observe($("clientembed-loader").select("a.quit-game").first(), "click", function(C) {
                Event.stop(C);
                HabboClientUtils.hideLoader();
                new Ajax.Request(habboReqPath + "/habblet/ajax/disableTaggingGame", {})
            })
        }
        this._enableObserverForTagCloud();
        $$("#tagging-game .category-tags").each(function(C, D) {
            A.checkFullStatus(C);
            A.categoriesCount++
        });
        A.showCurrent()
    },
    showCurrent: function() {
        $$("#tagging-game .category-tags").each(function(A, B) {
            if (B == this.currentCategoryShown) {
                A.show()
            } else {
                A.hide()
            }
        }.bind(this))
    },
    checkFullStatus: function(A) {
        if (typeof A == "undefined") {
            return
        }
        if ($(A).select(".tag-exists").length >= 3) {
            $(A).addClassName("full")
        } else {
            $(A).removeClassName("full")
        }
    },
    checkNextButtonStatus: function() {
        var A = $("tagging-game-contents").down("a.next-category");
        var B = $("category-1");
        if ($(B).select(".tag-exists").length >= 2) {
            $(A).removeClassName("disabled")
        } else {
            $(A).addClassName("disabled")
        }
    },
    addTag: function(A, B) {
        this._disableObserverForTagCloud();
        new Ajax.Request(habboReqPath + "/habblet/ajax/addTag", {
            parameters: {
                tagName: A,
                categoryId: B
            },
            onSuccess: function(J, I) {
                if (I != null && I.taglimit != null) {
                    $("taglimit").show()
                } else {
                    if (I != null && I.invalidtag != null) {
                        $("invalidtag").show()
                    } else {
                        if (I != null) {
                            var E = I.tags;
                            var G = I.selectedTags;
                            var H = I.categoryId;
                            var D = [];
                            D.push('<li id="category-' + H + '" class="category-tags" style="display: none">');
                            for (var F = 0; F < I.tags.length; F++) {
                                var C = E[F];
                                D.push('<a href="#"' + (G.match("," + C + ",") ? ' class="tag-exists"' : "") + "><span>" + C + "</span></a> &nbsp;")
                            }
                            D.push("</li>");
                            $("category-list").insert(D.join(""));
                            this.categoriesCount++
                        }
                        this.updateTagSelections(A, false);
                        $("taglimit").hide();
                        $("invalidtag").hide()
                    }
                }
                this._enableObserverForTagCloud()
            }.bind(this)
        })
    },
    removeTag: function(A, B) {
        this._disableObserverForTagCloud();
        new Ajax.Request(habboReqPath + "/habblet/ajax/removeTag", {
            parameters: {
                tagName: A,
                categoryId: B
            },
            onSuccess: function(E, D) {
                if (D != null) {
                    var C = D.categoryId;
                    $("category-list").removeChild($("category-" + C));
                    this.categoriesCount--
                }
                this.updateTagSelections(A, true);
                $("taglimit").hide();
                this._enableObserverForTagCloud()
            }.bind(this)
        })
    },
    onNextClick: function(B) {
        Event.stop(B);
        var A = ++this.currentCategoryShown;
        this.currentCategoryShown = Math.min(A, this.categoriesCount - 1);
        this.showCurrent();
        if (A > 0 && A == this.categoriesCount - 1) {
            $$("#tagging-game .next-category b").each(function(C) {
                C.update("( " + (this.categoriesCount) + " / " + (this.categoriesCount) + " ) " + L10N.get("tagging_game.done"))
            }.bind(this))
        } else {
            if (A >= this.categoriesCount) {
                this.finishGame()
            } else {
                this.updateNextButtonCounter()
            }
        }
    },
    finishGame: function() {
        this.currentCategoryShown = -1;
        this.showCurrent();
        $("tagging-game-contents").hide();
        if (this.onWeb) {
            $("contents-complete").show()
        } else {
            HabboClientUtils.hideLoader();
            this.disableTaggingGameForUser()
        }
    },
    onTagClick: function(F) {
        Event.stop(F);
        var E = Event.element(F);
        var C = $(E).up("a");
        var D = $(E).up(".category-tags");
        var G = D.id.substring("category-".length, D.id.length);
        var B = E.innerHTML.strip();
        var A = C.hasClassName("tag-exists");
        if (A) {
            this.removeTag(B, G)
        } else {
            this.addTag(B, G)
        }
    },
    updateTagSelections: function(B, A) {
        $("category-list").childElements().each(function(D) {
            var C = 0;
            if (D.id != "category-1" || this.currentCategoryShown == 0) {
                $(D).select("a").each(function(E) {
                    if (B == $(E).down("span").innerHTML.strip()) {
                        if (A) {
                            E.removeClassName("tag-exists")
                        } else {
                            E.addClassName("tag-exists")
                        }
                    }
                })
            }
            this.checkFullStatus(D);
            C++
        }.bind(this));
        if (this.currentCategoryShown == 0) {
            this.checkNextButtonStatus();
            this.updateNextButtonCounter()
        }
    },
    updateNextButtonCounter: function() {
        $$("#tagging-game .next-category b").each(function(A) {
            A.update("( " + (this.currentCategoryShown + 1) + " / " + (this.categoriesCount) + " ) " + L10N.get("tagging_game.next"))
        }.bind(this))
    },
    onRefresh: function(A) {
        Event.stop(A);
        this._disableObserverForTagCloud();
        new Ajax.Request(habboReqPath + "/habblet/ajax/refreshTaggingGame", {
            onSuccess: function(D, C) {
                $("tagging-game-contents").update(D.responseText);
                this.currentCategoryShown = 0;
                this.categoriesCount = 0;
                $$("#tagging-game .category-tags").each(function(E, F) {
                    this.checkFullStatus(E);
                    this.categoriesCount++
                }.bind(this));
                this.showCurrent();
                var B = $("contents-complete");
                if (B != null) {
                    B.hide()
                }
                $("tagging-game-contents").show();
                this._enableObserverForTagCloud()
            }.bind(this)
        })
    },
    disableTaggingGameForUser: function() {
        new Ajax.Request(habboReqPath + "/habblet/ajax/disableTaggingGame", {})
    },
    _enableObserverForTagCloud: function() {
        var A = this;
        Event.observe($("tagging-game"), "click", Event.delegate({
            "a.refresh": A.onRefresh.bind(A),
            "a.refresh b": A.onRefresh.bind(A),
            "a.next-category.disabled": function(B) {
                Event.stop(B)
            },
            "a.next-category.disabled b": function(B) {
                Event.stop(B)
            },
            ".full a.tag-exists span": A.onTagClick.bind(A),
            ".full a span": function(B) {
                Event.stop(B)
            },
            "a span": A.onTagClick.bind(A),
            "a.next-category": A.onNextClick.bind(A),
            "a.next-category b": A.onNextClick.bind(A)
        }))
    },
    _disableObserverForTagCloud: function() {
        Event.stopObserving($("tagging-game"), "click")
    }
};
var HabbletTabber = function() {
    var A = function(C, D, B) {
        if (C.indexOf("?") == -1) {
            C += "?"
        } else {
            C += "&"
        }
        C += "first=true";
        Element.wait(D, B);
        new Ajax.Updater(D, C, {
            method: "post",
            evalScripts: true
        })
    };
    return {
        init: function() {
            ($("content") || document.body).select(".box-tabs-container .box-tabs").each(function(B) {
                Event.observe(B, "click", HabbletTabber.onclickHandler);
                B.select(".selected").each(function(C) {
                    if (!!$(C.id + "-content")) {
                        $(C.id + "-content").select(".tab-ajax").each(function(D) {
                            A(D.href, D.parentNode)
                        })
                    }
                })
            })
        },
        onclickHandler: function(E) {
            var D = Event.findElement(E, "li");
            if (D && D.id && (!D.className || !Element.hasClassName(D, "selected"))) {
                var B = 0;
                D.parentNode.select(".selected").each(function(F) {
                    F.className = "";
                    B = $(F.id + "-content").getHeight();
                    Element.hide(F.id + "-content")
                });
                Element.addClassName(D, "selected");
                $(D.id + "-content").select(".tab-ajax").each(function(F) {
                    A(F.href, F.parentNode, B)
                });
                Element.show(D.id + "-content");
                Event.stop(E)
            } else {
                var C = Event.element(E).href;
                if (!!C && C.substring(C.length - 1) == "#") {
                    Event.stop(E)
                }
            }
        }
    }
}();
HabboView.add(HabbletTabber.init);
var TagHelper = Class.create();
TagHelper.initialized = false;
TagHelper.init = function(A) {
    if (TagHelper.initialized) {
        return
    }
    TagHelper.initialized = true;
    TagHelper.loggedInAccountId = A;
    TagHelper.bindEventsToTagLists()
};
TagHelper.addFormTagToMe = function() {
    var A = $("add-tag-input");
    TagHelper.addThisTagToMe($F(A), true);
    Form.Element.clear(A)
};
TagHelper.bindEventsToTagLists = function() {
    var A = function(B) {
        TagHelper.tagListClicked(B, TagHelper.loggedInAccountId)
    };
    $$(".tag-list.make-clickable").each(function(B) {
        Event.observe(B, "click", A);
        Element.removeClassName(B, "make-clickable")
    })
};
TagHelper.setTexts = function(A) {
    TagHelper.options = A
};
TagHelper.tagListClicked = function(E) {
    var D = Event.element(E);
    var B = Element.hasClassName(D, "tag-add-link");
    var A = Element.hasClassName(D, "tag-remove-link");
    if (B || A) {
        var F = Element.up(D, ".tag-list li");
        if (!F) {
            return
        }
        var C = TagHelper.findTagNameForContainer(F);
        Event.stop(E);
        if (B) {
            TagHelper.addThisTagToMe(C, true)
        } else {
            TagHelper.removeThisTagFromMe(C)
        }
    }
};
TagHelper.findTagNameForContainer = function(A) {
    var B = Element.down(A, ".tag");
    if (!B) {
        return null
    }
    return B.innerHTML.strip()
};
TagHelper.addThisTagToMe = function(B, C, A) {
    if (typeof(A) == "undefined") {
        A = {}
    }
    new Ajax.Request(habboReqPath + "/myhabbo/tag/add", {
        parameters: "accountId=" + encodeURIComponent(TagHelper.loggedInAccountId) + "&tagName=" + encodeURIComponent(B),
        onSuccess: function(E) {
            var D = E.responseText;
            if (D == "valid" && C) {
                $$(".tag-list li").each(function(F) {
                    if (TagHelper.findTagNameForContainer(F) == B) {
                        var H = Element.down(F, ".tag-add-link");
                        var G = $$(".tag-remove-link").first();
                        H.title = G ? G.title : "";
                        H.removeClassName("tag-add-link").addClassName("tag-remove-link")
                    }
                })
            } else {
                if (D == "taglimit") {
                    Dialog.showInfoDialog("tag-error-dialog", TagHelper.options.tagLimitText, TagHelper.options.buttonText, null)
                } else {
                    if (D == "invalidtag") {
                        Dialog.showInfoDialog("tag-error-dialog", TagHelper.options.invalidTagText, TagHelper.options.buttonText, null)
                    }
                }
            }
            if (D == "valid" || D == "") {
                if (C) {
                    TagHelper.reloadMyTagsList()
                } else {
                    TagHelper.reloadSearchBox(B, 1)
                }
                if (typeof(A.onSuccess) == "function") {
                    A.onSuccess()
                }
            }
        }
    })
};
TagHelper.reloadSearchBox = function(A, B) {
    if ($("tag-search-habblet-container")) {
        new Ajax.Updater($("tag-search-habblet-container"), habboReqPath + "/habblet/ajax/tagsearch", {
            method: "post",
            parameters: "tag=" + A + "&pageNumber=" + B,
            evalScripts: true
        })
    }
};
TagHelper.removeThisTagFromMe = function(A) {
    new Ajax.Request(habboReqPath + "/myhabbo/tag/remove", {
        parameters: "accountId=" + encodeURIComponent(TagHelper.loggedInAccountId) + "&tagName=" + encodeURIComponent(A),
        onSuccess: function(C) {
            var B = function(D) {
                $$(".tag-list li").each(function(E) {
                    if (TagHelper.findTagNameForContainer(E) == A) {
                        var G = Element.down(E, ".tag-remove-link");
                        var F = $$(".tag-add-link").first();
                        if (F) {
                            G.title = F.title || "";
                            G.removeClassName("tag-remove-link").addClassName("tag-add-link")
                        }
                    }
                })
            };
            TagHelper.reloadMyTagsList({
                onSuccess: B
            })
        }
    })
};
TagHelper.reloadMyTagsList = function(B) {
    var A = {
        evalScripts: true
    };
    Object.extend(A, B);
    new Ajax.Updater($("my-tags-list"), habboReqPath + "/habblet/mytagslist", A)
};
TagHelper.matchFriend = function() {
    var A = $F("tag-match-friend");
    if (A) {
        new Ajax.Updater($("tag-match-result"), habboReqPath + "/habblet/ajax/tagmatch", {
            parameters: {
                friendName: A
            },
            onComplete: function(D) {
                var C = $("tag-match-value");
                if (C) {
                    var B = parseInt(C.innerHTML, 10);
                    if (typeof TagHelper.CountEffect == "undefined") {
                        $("tag-match-value-display").innerHTML = B + " %";
                        Element.show("tag-match-slogan")
                    } else {
                        var E;
                        if (B > 0) {
                            E = 1.5
                        } else {
                            E = 0.1
                        }
                        new TagHelper.CountEffect("tag-match-value-display", {
                            duration: E,
                            transition: Effect.Transitions.sinoidal,
                            from: 0,
                            to: B,
                            afterFinish: function() {
                                Effect.Appear("tag-match-slogan", {
                                    duration: 1
                                })
                            }
                        })
                    }
                }
            }
        })
    }
};
var TagFight = Class.create();
TagFight.init = function() {
    if ($F("tag1") && $F("tag2")) {
        TagFight.start()
    } else {
        return false
    }
};
TagFight.start = function() {
    $("fightForm").style.display = "none";
    $("tag-fight-button").style.display = "none";
    $("fightanimation").src = habboStaticFilePath + "/images/tagfight/tagfight_loop.gif";
    $("fight-process").style.display = "block";
    setTimeout("TagFight.run()", 3000)
};
TagFight.run = function() {
    new Ajax.Updater("fightResults", habboReqPath + "/habblet/ajax/tagfight", {
        method: "post",
        parameters: "tag1=" + $F("tag1") + "&tag2=" + $F("tag2"),
        onComplete: function() {
            $("fight-process").style.display = "none";
            $("fightForm").style.display = "none";
            $("tag-fight-button-new").style.display = "block"
        }
    })
};
TagFight.newFight = function() {
    $("fight-process").style.display = "none";
    $("fightForm").style.display = "block";
    $("fightResultCount").style.display = "none";
    $("tag-fight-button").style.display = "block";
    $("tag-fight-button-new").style.display = "none";
    $("fightanimation").src = habboStaticFilePath + "/images/tagfight/tagfight_start.gif";
    $("tag1").value = "";
    $("tag2").value = ""
};
QuickMenu = Class.create();
QuickMenu.prototype = {
    initialize: function() {},
    add: function(A, B) {
        new QuickMenuItem(this, A, B)
    },
    activate: function(A) {
        var B = A.element;
        if (this.active) {
            Element.removeClassName(this.active, "selected")
        }
        if (this.active === B) {
            this.closeContainer()
        } else {
            Element.addClassName(B, "selected");
            if (this.openContainer(B)) {
                if (A.clickHandler) {
                    A.clickHandler.apply(null, [this.qtabContainer])
                }
            }
        }
    },
    openContainer: function(B) {
        var C = $("the-qtab-" + B.id);
        var D = (C == null);
        if (D) {
            var C = $(Builder.node("div", {
                "class": "the-qtab",
                id: "the-qtab-" + B.id
            }));
            $("header").appendChild(C);
            var A = '<div style="margin-left: 1px; width: ' + (B.getWidth() - 2) + 'px; height: 1px; background-color: #fff"></div>';
            C.update('<div class="qtab-container-top">' + A + '</div><div class="qtab-container-bottom"><div id="qtab-container-' + B.id + '" class="qtab-container"></div></div>');
            this.qtabContainer = $("qtab-container-" + B.id);
            C.clonePosition(B, {
                setWidth: false,
                setHeight: false,
                offsetTop: 25
            })
        }
        $("header").select(".the-qtab").each(Element.hide);
        C.show();
        this.active = B;
        return D
    },
    closeContainer: function() {
        $("header").select(".the-qtab").each(Element.hide);
        if (this.active) {
            var A = $("the-qtab-" + this.active.id);
            Element.removeClassName(this.active, "selected")
        }
        this.active = null
    }
};
QuickMenuItem = Class.create();
QuickMenuItem.prototype = {
    initialize: function(A, C, D) {
        this.quickMenu = A;
        this.element = $(C);
        var B = this.click.bind(this);
        C.down("a").observe("click", B);
        if (D) {
            this.clickHandler = D
        }
    },
    click: function(A) {
        Event.stop(A);
        this.quickMenu.activate(this)
    }
};
HabboView.add(function() {
    if (document.habboLoggedIn && $("subnavi-user")) {
        var B = new QuickMenu();
        var A = $A([
            ["myfriends", habboReqPath + "/quickmenu/friends_all"],
            ["mygroups", habboReqPath + "/quickmenu/groups"],
            ["myrooms", habboReqPath + "/quickmenu/rooms"]
        ]);
        A.each(function(C) {
            B.add($(C[0]), function(D) {
                var E = C[1];
                Element.wait(D);
                new Ajax.Updater(D, E, {
                    onComplete: function() {
                        new QuickMenuListPaging(C[0], E)
                    }
                })
            })
        });
        Event.observe(document.body, "click", function(C) {
            B.closeContainer()
        })
    }
});
var Accordion = Class.create();
Accordion.prototype = {
    initialize: function(F, E, A, C, D, B) {
        this.animating = false;
        this.openedItem = null;
        this.accordionContainer = F;
        this.summaryContainerPrefix = E;
        this.toggleDetailsClassName = A;
        this.detailsContainerPrefix = C;
        this.openDetailsL10NKey = D;
        this.closeDetailsL10NKey = B;
        this.accordionContainer.select("." + this.toggleDetailsClassName).each(function(H) {
            var G = this.parseItem(H);
            if (G.el.visible()) {
                this.openedItem = G;
                throw $break
            }
        }.bind(this));
        Event.observe(this.accordionContainer, "click", function(I) {
            var H = Event.element(I);
            if (H && H.id && H.hasClassName(this.toggleDetailsClassName)) {
                Event.stop(I);
                var G = this.parseItem(H);
                if (G.el) {
                    this.toggleItems(G.link, G.el, G.id)
                }
            }
        }.bind(this))
    },
    parseItem: function(B) {
        var C = B.id.split("-").last();
        var A = $(this.detailsContainerPrefix + C);
        return {
            link: B,
            el: A,
            id: C
        }
    },
    toggleItems: function(D, B, E) {
        if (this.animating) {
            return false
        }
        var A = this.openedItem;
        var C = [];
        if (!A || (A && A.id != E)) {
            $(this.summaryContainerPrefix + E).addClassName("selected");
            if (this.closeDetailsL10NKey) {
                D.innerHTML = L10N.get(this.closeDetailsL10NKey)
            }
            C.push(new Effect.BlindDown(B));
            this.openedItem = {
                link: D,
                el: B,
                id: E
            }
        }
        if (A && A.id == E) {
            this.openedItem = null
        }
        if (A) {
            $(this.summaryContainerPrefix + A.id).removeClassName("selected");
            if (this.openDetailsL10NKey) {
                A.link.innerHTML = L10N.get(this.openDetailsL10NKey)
            }
            C.push(new Effect.BlindUp(A.el))
        }
        new Effect.Parallel(C, {
            queue: {
                position: "end",
                scope: "accordionAnimation"
            },
            beforeStart: function(F) {
                this.animating = true
            }.bind(this),
            afterFinish: function(F) {
                this.animating = false
            }.bind(this)
        })
    }
};
var RememberMeUI = {};
RememberMeUI.init = function(E) {
    E = E || "left";
    var B = $("login-remember-me");
    if (B) {
        var D = B.positionedOffset();
        var C = {
            top: (D[1] + 20) + "px"
        };
        if (E == "right") {
            C.right = (D[0] - 190) + "px"
        } else {
            if (E == "newfrontpage") {
                var F = 15,
                    A = -14;
                if (Prototype.Browser.IE) {
                    F = 20;
                    A = -10
                }
                C.top = (D[1] + F) + "px";
                C.left = (D[0] + A) + "px"
            } else {
                C.left = (D[0] - 10) + "px"
            }
        }
        $("remember-me-notification").setStyle(C);
        Event.observe(B, "click", function(G) {
            $("remember-me-notification").setStyle({
                display: B.checked ? "block" : "none"
            })
        })
    }
};
var SearchBoxHelper = Class.create();
SearchBoxHelper.initialized = false;
SearchBoxHelper.originalValues = [];
SearchBoxHelper.init = function() {
    if (SearchBoxHelper.initialized) {
        return
    }
    SearchBoxHelper.initialized = true;
    SearchBoxHelper.bindEventListeners()
};
SearchBoxHelper.bindEventListeners = function() {
    var B = function(C) {
        SearchBoxHelper.onBoxFocus(C)
    };
    var A = function(C) {
        SearchBoxHelper.onBoxBlur(C)
    };
    $$(".search-box-onfocus").each(function(C) {
        Event.observe(C, "focus", B);
        Event.observe(C, "blur", A);
        SearchBoxHelper.originalValues[C] = C.value;
        Element.removeClassName(C, "search-box-onfocus")
    })
};
SearchBoxHelper.onBoxFocus = function(B) {
    var A = Event.element(B);
    if (A.value == SearchBoxHelper.originalValues[A]) {
        A.value = "";
        A.style.color = "#333333"
    } else {
        A.select()
    }
};
SearchBoxHelper.onBoxBlur = function(B) {
    var A = Event.element(B);
    if (A.value == "") {
        A.value = SearchBoxHelper.originalValues[A];
        A.style.color = "#777777"
    }
};
var NewsPromo = function() {
    var B = [];
    var C = 0;
    var E = false;
    var A = false;
    var D = function(F) {
        if (!E && !A) {
            A = true;
            Effect.Fade(B[C], {
                duration: 0.8,
                from: 1,
                to: 0
            });
            C = C + F;
            if (C == B.length) {
                C = 0
            }
            if (C == -1) {
                C = B.length - 1
            }
            Effect.Appear(B[C], {
                duration: 0.8,
                from: 0,
                to: 1,
                afterFinish: function() {
                    A = false
                }
            });
            if ($("topstories-nav")) {
                $("topstories-nav").down("span").update(C + 1)
            }
        }
    };
    return {
        init: function() {
            B = $$("#topstories .topstory");
            if (B.length < 2) {
                return
            }
            Event.observe("topstories", "mouseover", function(G) {
                E = true;
                $$("#topstories-nav").each(Element.show)
            });
            Event.observe("topstories", "mouseout", function(G) {
                E = false;
                $$("#topstories-nav").each(Element.hide)
            });
            if ($("topstories-nav")) {
                Event.observe($("topstories-nav").down("a.next"), "click", function(G) {
                    Event.stop(G);
                    E = false;
                    D(1);
                    E = true
                });
                Event.observe($("topstories-nav").down("a.prev"), "click", function(G) {
                    Event.stop(G);
                    E = false;
                    D(-1);
                    E = true
                })
            }
            var F = 10000;
            setInterval(function() {
                D(1)
            }, F)
        }
    }
}();
var LoginFormUI = {
    init: function() {
        if ($("login-submit-button")) {
            $("login-submit-button").setStyle({
                marginLeft: "-10000px"
            });
            $("login-submit-new-button").show();
            $("login-submit-new-button").observe("click", function(A) {
                Event.stop(A);
                $("login-submit-new-button").up("form").submit()
            });
            if (!!$("subnavi-login")) {
                $$("input.login-field").invoke("observe", "focus", function() {
                    $("subnavi-login").addClassName("focused")
                });
                $$("input.login-field").invoke("observe", "blur", function() {
                    $("subnavi-login").removeClassName("focused")
                })
            }
        }
    }
};
PersonalInfo = {};
PersonalInfo.init = function(F) {
    var M = $("motto-container");
    var A = M.down("strong").getWidth();
    var I = 329;
    M.down("div").setStyle({
        width: I - A - 12 + "px"
    });
    var E = M.down("input");
    E.setStyle({
        width: I - A - 15 + "px"
    });
    var D = M.down("span");
    var B = M.down("p");
    var K = $("motto-links");
    var G = function() {
        D.hide();
        B.show();
        E.value = F;
        E.focus();
        E.select();
        K.show()
    };
    var L = function() {
        B.hide();
        D.show();
        K.hide()
    };
    var C = function() {
        L();
        if (F == E.value) {
            return
        }
        var N = D.innerHTML;
        D.update('<img src="' + habboStaticFilePath + '/images/progress_bubbles.gif" width="29" height="6" alt="" class="progresshabboReqPath+"/>');
        new Ajax.Request(habboReqPath + "/habblet/ajax/updatemotto", {
            parameters: {
                motto: E.value
            },
            onSuccess: function(P, O) {
                if (O && O.spamming === true) {
                    alert(L10N.get("personal_info.motto_editor.spamming"));
                    E.value = F;
                    D.update(N);
                    return
                }
                if (O) {
                    F = O.motto
                }
                D.update(P.responseText);
                if (O && O.specialAvatar) {
                    document.location.replace(document.location.href)
                }
                if (typeof pageTracker != "undefined") {
                    pageTracker._trackEvent("motto", "changed")
                }
            }
        })
    };
    Event.observe(D, "click", G);
    Event.observe(E, "keypress", function(N) {
        if (N.keyCode == Event.KEY_RETURN) {
            C()
        } else {
            if (N.keyCode == Event.KEY_ESC) {
                L()
            }
        }
    });
    Event.observe($("motto-cancel"), "click", function(N) {
        Event.stop(N);
        L()
    });
    if ($("show-all-friends")) {
        Event.observe("show-all-friends", "click", function(N) {
            Event.stop(N);
            el = Event.element(N);
            el.replace('<img src="' + habboStaticFilePath + '/images/progress_bubbles.gif" width="29" height="6" alt="habboReqPath+"/>');
            new Ajax.Request(habboReqPath + "/habblet/ajax/allfriends", {
                onSuccess: function(O) {
                    $("feed-friends").down("span").update(O.responseText)
                }
            })
        })
    }
    Event.observe("feed-items", "click", Event.delegate({
        "li.contributed .remove-feed-item": function(P) {
            Event.stop(P);
            var N = this.up();
            var O = N.up().select("li.contributed").indexOf(N);
            N.remove();
            new Ajax.Request(habboReqPath + "/habblet/ajax/removeFeedItem", {
                parameters: {
                    feedItemIndex: O
                }
            })
        }
    }));
    if (!!$("feed-item-hc-reminder")) {
        var H = function() {
            var N = $("feed-item-hc-reminder");
            N.remove();
            new Ajax.Request(habboReqPath + "/habboclub/habboclub_reminder_remove")
        };
        habboclub._updateMembershipCallback = H;
        $("remove-hc-reminder").observe("click", function(N) {
            Event.stop(N);
            H()
        });
        $$("#hc-reminder-buttons a.new-button").invoke("observe", "click", function(O) {
            Event.stop(O);
            var N = Event.findElement(O, "a");
            if (N && N.id) {
                habboclub.buttonClick(N.id.split("-").last(), L10N.get("subscription.title"))
            }
        })
    }
    if (!!$("remove-tutor-score")) {
        var J = function() {
            var N = $("feed-item-tutor-score");
            if (N) {
                N.remove()
            }
            new Ajax.Request(habboReqPath + "/habblet/tutor_score_feed_remove")
        };
        $("remove-tutor-score").observe("click", function(N) {
            Event.stop(N);
            J()
        })
    }
    if (!!$("feed-flashbeta-invites")) {
        $($("feed-flashbeta-invites").select("button")[0]).observe("click", function(N) {
            Event.stop(N);
            $("feed-flashbeta-invites").wait();
            new Ajax.Updater("feed-flashbeta-invites", habboReqPath + "/groups/actions/create_invite")
        })
    }
};
var GroupUtils = {
    validateGroupElements: function(C, A, E) {
        var D = $(C);
        if (D.value.length >= A) {
            D.value = D.value.substring(0, A);
            $(C + "_message_error").innerHTML = E;
            $(C + "_message_error").style.display = "block"
        } else {
            $(C + "_message_error").innerHTML = "";
            $(C + "_message_error").style.display = "none"
        }
        if ($(C + "-counter")) {
            var B = A - D.value.length;
            $(C + "-counter").value = B
        }
    }
};
var GroupPurchase = function() {
    var A = null;
    var C = null;
    var B = function(D) {
        Dialog.setAsWaitDialog(A);
        new Ajax.Request(habboReqPath + "/grouppurchase/" + D, {
            parameters: C,
            onComplete: function(F, E) {
                Dialog.setDialogBody(A, F.responseText)
            }
        })
    };
    return {
        open: function() {
            A = Dialog.createDialog("group_purchase_form", L10N.get("purchase.group.title"), 9001, 0, -1000, GroupPurchase.close);
            Dialog.setAsWaitDialog(A);
            Dialog.moveDialogToCenter(A);
            Dialog.makeDialogDraggable(A);
            Overlay.show();
            new Ajax.Request(habboReqPath + "/grouppurchase/group_create_form", {
                onComplete: function(E, D) {
                    Dialog.setDialogBody(A, E.responseText)
                }
            })
        },
        close: function(D) {
            if (!!D) {
                Event.stop(D)
            }
            $("group_purchase_form").remove();
            Overlay.hide();
            A = null;
            C = null
        },
        confirm: function() {
            C = {
                name: $F("group_name"),
                description: $F("group_description")
            };
            B("purchase_confirmation")
        },
        purchase: function() {
            B("purchase_ajax");
            if ($("the-qtab-mygroups")) {
                $("the-qtab-mygroups").remove()
            }
        }
    }
}();
var LinkTool = Class.create();
LinkTool.prototype = {
    initialize: function(A, B) {
        this.elements = Object.extend({
            button: $("linktool-find"),
            input: $("linktool-query"),
            results: $("linktool-results"),
            scopeButtons: $$(".linktool-scope")
        }, B || {});
        this.textarea = A;
        this.elements.results.hide();
        Event.observe(this.elements.button, "click", this.search.bind(this));
        Event.observe(this.elements.input, Prototype.Browser.IE ? "keydown" : "keypress", function(C) {
            if (C.keyCode == Event.KEY_RETURN) {
                this.search(C)
            }
        }.bind(this));
        Event.observe(this.elements.results, "click", function(D) {
            var C = D.findElement("a");
            if (C && C.hasClassName("linktool-result")) {
                D.stop();
                this.addLink(C.readAttribute("type"), C.readAttribute("value"), C.readAttribute("title"))
            }
        }.bind(this));
        this.elements.scopeButtons.invoke("observe", "click", function(C) {
            if (!!$F(this.elements.input) && this.elements.results.visible()) {
                this.search()
            }
        }.bind(this))
    },
    addLink: function(A, B, C) {
        if (!this.textarea.getSelection() || this.textarea.getSelection() == "") {
            this.textarea.replaceSelection("[" + A + "=" + B + "]" + C + "[/" + A + "]")
        } else {
            this.textarea.wrapSelection("[" + A + "=" + B + "]", "[/" + A + "]")
        }
        this.elements.results.hide()
    },
    search: function(C) {
        if (!!C) {
            Event.stop(C)
        }
        this.elements.results.setStyle({
            display: "block"
        });
        var B = $F(this.elements.input);
        if (!!B) {
            this.elements.input.removeClassName("error");
            this.elements.results.wait();
            var A = 1;
            this.elements.scopeButtons.each(function(D) {
                if (D.checked) {
                    A = D.value
                }
            });
            new Ajax.Updater(this.elements.results, habboReqPath + "/myhabbo/linktool/search", {
                method: "get",
                parameters: {
                    query: B,
                    scope: A
                }
            })
        } else {
            this.elements.input.addClassName("error")
        }
    }
};