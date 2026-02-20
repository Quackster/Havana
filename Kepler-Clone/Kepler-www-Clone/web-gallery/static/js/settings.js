HabboView.add(function() {
    HabboEditor.addCallback("setGender", function(A) {
        $("settings-gender").value = A
    });
    HabboEditor.addCallback("setFigure", function(A) {
        $("settings-figure").value = A
    });
    HabboEditor.addCallback("setEditorState", function(A) {
        $("settings-state").value = A
    });
    HabboEditor.addCallback("setAllowedToProceed", function(A) {
        if (A) {
            $("settings-submit").removeClassName("disabled-button");
            HabboEditor.hideHabboClubNotice()
        } else {
            $("settings-submit").addClassName("disabled-button")
        }
    });
    HabboEditor.addCallback("showHabboClubNotice", function(A) {
        $("settings-hc").show()
    });
    HabboEditor.addCallback("hideHabboClubNotice", function(A) {
        $("settings-hc").hide()
    });
    HabboEditor.addCallback("showOldFigureNotice", function(A) {
        $("settings-oldfigure").show()
    });
    HabboEditor.addCallback("hideOldFigureNotice", function(A) {
        $("settings-oldfigure").hide()
    })
});
HabboView.add(function() {
    if (!!$("settings-form")) {
        $("settings-form").observe("submit", function(A) {
            if (!HabboEditor.isAllowedToProceed()) {
                Event.stop(A)
            }
        });
        $("settings-submit").observe("click", function(A) {
            Event.stop(A);
            if (HabboEditor.isAllowedToProceed()) {
                $("settings-form").submit()
            }
        })
    }
});
var Wardrobe = function() {
    var C = false;
    var D = [];
    var B = function(G, F) {
        $("wardrobe-slot-" + G).setStyle({
            backgroundImage: "url(" + F + ")"
        })
    };
    var E = function(J, F, H) {
        Overlay.show();
        var G = Dialog.createDialog("wardrobe-replace", L10N.get("profile.figure.wardrobe_replace.title"));
        Dialog.setDialogBody(G, L10N.get("profile.figure.wardrobe_replace.dialog"));
        var I = function(K) {
            Event.stop(K);
            $("wardrobe-replace").remove();
            Overlay.hide()
        };
        $("wardrobe-replace-cancel").observe("click", I);
        $("wardrobe-replace-ok").observe("click", function(K) {
            A(J, F, H);
            I(K)
        });
        Dialog.moveDialogToCenter(G);
        Dialog.makeDialogDraggable(G);
        return true
    };
    var A = function(H, F, G) {
        Wardrobe.add(H, F, G);
        new Ajax.Request(habboReqPath + "/profile/wardrobeStore", {
            parameters: {
                slot: H,
                figure: F,
                gender: G
            },
            onComplete: function(J, I) {
                if (J.responseJSON.e) {
                    alert(L10N.get("profile.figure.wardrobe_invalid_data"))
                } else {
                    B(J.responseJSON.slot, J.responseJSON.u);
                    $("wardrobe-dress-" + J.responseJSON.slot).show()
                }
            }
        })
    };
    return {
        init: function(F) {
            C = F;
            $$("span.wardrobe-dress").invoke("observe", "click", Wardrobe.dress);
            $$("span.wardrobe-store").invoke("observe", "click", Wardrobe.store);
            Event.observe(window, "load", function() {
                $("content").insert('<div id="wardrobe-instructions" style="display: none"><div class="bubbletip left"><div class="bubbletip-title"></div><div class="content">' + L10N.get("profile.figure.wardrobe_instructions") + "</div></div></div>");
                $("wardrobe-instructions").setStyle({
                    top: ($("wardrobe-slots").offsetTop - $("wardrobe-instructions").getHeight() - 6) + "px"
                });
                $("settings-wardrobe").observe("mouseover", function() {
                    $("wardrobe-instructions").show()
                });
                $("settings-wardrobe").observe("mouseout", function() {
                    $("wardrobe-instructions").hide()
                })
            })
        },
        add: function(I, F, G, H) {
            D[I] = {
                f: F,
                g: G,
                hc: H
            }
        },
        store: function(J) {
            var I = Event.element(J);
            if (I && I.id) {
                var G = I.id.split("-").last();
                if (G > 0) {
                    var F = $("settings-figure").value;
                    var H = $("settings-gender").value;
                    if (!D[G] || !E(G, F, H)) {
                        A(G, F, H)
                    }
                }
            }
        },
        dress: function(H) {
            var G = Event.element(H);
            if (G && G.id) {
                var F = G.id.split("-").last();
                if (D[F] && $("settings-figure").value != D[F].f) {
                    HabboEditor.setGender(D[F].g);
                    HabboEditor.setFigure(D[F].f);
                    swfobj.addVariable("figure", D[F].f);
                    swfobj.addVariable("gender", D[F].g);
                    swfobj.addVariable("showClubSelections", (C) ? "1" : "0");
                    swfobj.write("settings-editor")
                }
            }
        }
    }
}();
var HabboEditor = function() {
    var D = true;
    var A = null;
    var B = null;
    var C = [];
    return {
        addCallback: function(E, F) {
            if (!C[E]) {
                C[E] = []
            }
            C[E].push(F)
        },
        setGenderAndFigure: function(F, E) {
            this.setGender(F);
            this.setFigure(E)
        },
        setFigure: function(E) {
            A = E;
            if (C.setFigure) {
                C.setFigure.each(function(F) {
                    F(E)
                })
            }
        },
        setGender: function(E) {
            B = E;
            if (C.setGender) {
                C.setGender.each(function(F) {
                    F(E)
                })
            }
        },
        setAllowedToProceed: function(E) {
            D = E;
            if (C.setAllowedToProceed) {
                C.setAllowedToProceed.each(function(F) {
                    F(E)
                })
            }
        },
        isAllowedToProceed: function() {
            return D
        },
        showHabboClubNotice: function() {
            if (C.showHabboClubNotice) {
                C.showHabboClubNotice.each(function(E) {
                    E()
                })
            }
        },
        hideHabboClubNotice: function() {
            if (C.hideHabboClubNotice) {
                C.hideHabboClubNotice.each(function(E) {
                    E()
                })
            }
        },
        showOldFigureNotice: function() {
            if (C.showOldFigureNotice) {
                C.showOldFigureNotice.each(function(E) {
                    E()
                })
            }
        },
        hideOldFigureNotice: function() {
            if (C.hideOldFigureNotice) {
                C.hideOldFigureNotice.each(function(E) {
                    E()
                })
            }
        },
        setEditorState: function(E) {
            if (C.setEditorState) {
                C.setEditorState.each(function(F) {
                    F(E)
                })
            }
        }
    }
}();
var FriendManagement = Class.create({
    initialize: function(F) {
        var A = this;
        var O = {
            currentCategoryId: 0,
            pageListLimit: 30,
            pageNumber: 1,
            sortColumn: null
        };
        A.options = Object.extend(O, F || {});
        var L = function(a) {
            A.options.currentCategoryId = a
        };
        var H = function(a) {
            A.options.pageListLimit = a
        };
        var X = function(a) {
            A.options.pageNumber = a
        };
        var Q = function(a) {
            A.options.sortColumn = a
        };
        var G = function(a) {
            Tips.get(a).hideTip()
        };
        var J = function() {
            new Ajax.Updater("category-options", habboReqPath + "/friendmanagement/ajax/updatecategoryoptions", {
                method: "get"
            })
        };
        var K = function() {
            var b = $("friend-list-form").serialize();
            if (b) {
                var c = $("category-list-select");
                var a = c.options[c.selectedIndex].value;
                if (a != 0) {
                    b = b + "&moveCategoryId=" + a
                }
                if (A.options.currentCategoryId != 0) {
                    b = b + "&categoryId=" + A.options.currentCategoryId
                }
                new Ajax.Updater("friend-list", habboReqPath + "/friendmanagement/ajax/movefriends", {
                    method: "post",
                    parameters: b + "&pageSize=" + A.options.pageListLimit
                })
            }
        };
        var T = function() {
            var b = "pageNumber=" + A.options.pageNumber + "&pageSize=" + A.options.pageListLimit;
            var a = $("friend_query").value;
            if (a != "") {
                b = b + "&searchString=" + a
            }
            if (A.options.currentCategoryId != 0) {
                b = b + "&categoryId=" + A.options.currentCategoryId
            }
            if (A.options.sortColumn) {
                b = b + "&sortColumn=" + A.options.sortColumn
            }
            new Ajax.Updater("friend-list", habboReqPath + "/friendmanagement/ajax/viewcategory", {
                method: "get",
                parameters: b,
                onComplete: function(d, c) {
                    $("category-item-" + A.options.currentCategoryId).addClassName("selected-category")
                }
            })
        };
        var Z = function(a) {
            H(a.id.substring(10));
            T()
        };
        var P = function(a) {
            var b = a.id.substring(14);
            if (b != A.options.currentCategoryId) {
                $("friend_query").value = "";
                $("category-item-" + A.options.currentCategoryId).removeClassName("selected-category");
                L(b);
                T()
            }
        };
        var N = function(a) {
            X(a.id.substring(5));
            T()
        };
        var V = function(a) {
            var b = a.id.substring(16);
            Tips.get("category-button-delete-" + b).hideTip();
            new Ajax.Updater("category-list", habboReqPath + "/friendmanagement/ajax/deletecategory", {
                method: "post",
                parameters: "categoryId=" + b,
                onComplete: function(d, c) {
                    J();
                    L(0);
                    X(1);
                    T()
                }
            })
        };
        var M = function(a) {
            var c = a.id.substring(23);
            var b = L10N.get("friendmanagement.tooltip.deletecategory").replace(/\%category_id\%/g, c);
            new Tip("category-button-delete-" + c, b, {
                className: "bubbletip left",
                title: " ",
                hook: {
                    target: "topRight",
                    tip: "bottomRight"
                },
                offset: {
                    x: 130,
                    y: -3
                },
                startEvent: null,
                endEvent: null
            });
            Tips.get("category-button-delete-" + c).showTip();
            Event.observe("delete-category-" + c, "click", function(d) {
                Event.stop(d);
                V(Event.element(d))
            });
            Event.observe("cancel-cat-delete-" + c, "click", function(d) {
                Event.stop(d);
                G("category-button-delete-" + c)
            })
        };
        var U = function() {
            var a = $("friend_query").value;
            a = a.strip();
            if (a) {
                Q(null);
                new Ajax.Updater("friend-list", habboReqPath + "/friendmanagement/ajax/viewcategory", {
                    method: "post",
                    parameters: "searchString=" + encodeURIComponent(a) + "&pageSize=" + A.options.pageListLimit
                })
            }
        };
        var Y = function(b) {
            var a = "friendId=" + b + "&pageSize=" + A.options.pageListLimit;
            Tips.get("remove-friend-button-" + b).hideTip();
            if (A.options.currentCategoryId != 0) {
                a = a + "&categoryId=" + A.options.currentCategoryId
            }
            new Ajax.Updater("friend-list", habboReqPath + "/friendmanagement/ajax/deletefriends", {
                method: "post",
                parameters: a
            })
        };
        var W = function(a) {
            var c = a.id.substring(21);
            var b = L10N.get("friendmanagement.tooltip.deletefriend").replace(/\%friend_id\%/g, c);
            new Tip("remove-friend-button-" + c, b, {
                className: "bubbletip left",
                title: " ",
                hook: {
                    target: "topRight",
                    tip: "bottomRight"
                },
                offset: {
                    x: 60,
                    y: -3
                },
                startEvent: null,
                endEvent: null
            });
            Tips.get("remove-friend-button-" + c).showTip();
            Event.observe($("delete-friend-" + c), "click", function(d) {
                Y(c)
            });
            Event.observe($("remove-friend-can-" + c), "click", function(d) {
                G("remove-friend-button-" + c)
            })
        };
        var E = function() {
            Tips.remove($("delete-friends"));
            var a = $("friend-list-form").serialize();
            if (a) {
                if (A.options.currentCategoryId != 0) {
                    a = a + "&categoryId=" + A.options.currentCategoryId
                }
                new Ajax.Updater("friend-list", habboReqPath + "/friendmanagement/ajax/deletefriends", {
                    method: "post",
                    parameters: a + "&pageSize=" + A.options.pageListLimit
                })
            }
            Event.observe($("delete-friends"), "click", function(b) {
                Event.stop(b);
                S()
            })
        };
        var S = function() {
            new Tip("delete-friends", L10N.get("friendmanagement.tooltip.deletefriends"), {
                className: "bubbletip left",
                title: " ",
                hook: {
                    target: "topRight",
                    tip: "bottomRight"
                },
                offset: {
                    x: 60,
                    y: -3
                },
                startEvent: null,
                endEvent: null
            });
            Tips.get("delete-friends").showTip();
            Event.observe($("delete-friends-button"), "click", function(a) {
                E()
            });
            Event.observe($("cancel-delete-friends"), "click", function(a) {
                G("delete-friends")
            })
        };
        var I = function() {
            var a = $F("category-name");
            new Ajax.Updater("category-list", habboReqPath + "/friendmanagement/ajax/createcategory", {
                method: "post",
                parameters: "name=" + encodeURIComponent(a),
                onComplete: function(c, b) {
                    J()
                }
            })
        };
        var C = function(b) {
            var c = b.id.substring(21);
            $("category-name-" + c).hide();
            $("category-field-" + c).show();
            var a = $("category-input-" + c);
            a.focus();
            a.select();
            $("category-button-delete-" + c).hide();
            $("category-button-edit-" + c).hide();
            $("category-button-cancel-" + c).show();
            $("category-button-save-" + c).show()
        };
        var R = function(a) {
            var b = a.id.substring(23);
            $("category-name-" + b).show();
            $("category-field-" + b).hide();
            $("category-button-delete-" + b).show();
            $("category-button-edit-" + b).show();
            $("category-button-cancel-" + b).hide();
            $("category-button-save-" + b).hide()
        };
        var B = function(a) {
            var b = /^category[^\d]*([\d]+)$/.exec(a.id)[1];
            var c = $("category-input-" + b).value;
            new Ajax.Updater("category-list", habboReqPath + "/friendmanagement/ajax/editCategory", {
                method: "post",
                parameters: "name=" + encodeURIComponent(c) + "&categoryId=" + b,
                onComplete: function(e, d) {
                    J()
                }
            })
        };
        var D = function(a) {
            var c = Element.up(a, "th");
            var d = $w(c.className).grep(/^friend\-/);
            if (d.length > 0) {
                var b = d[0].substr(d[0].indexOf("-") + 1);
                Q(b);
                T()
            }
        };
        Event.observe($("friend-management-container"), "click", Event.delegate({
            ".category-default": function(a) {
                Event.stop(a);
                X(1);
                P(Event.element(a))
            },
            ".open-category": function(a) {
                Event.stop(a);
                X(1);
                P(Event.element(a))
            },
            ".delete-category-tip": function(a) {
                Event.stop(a);
                M(Event.element(a))
            },
            ".edit-category": function(a) {
                Event.stop(a);
                C(Event.element(a))
            },
            ".cancel-edit-category": function(a) {
                Event.stop(a);
                R(Event.element(a))
            },
            ".save-category": function(a) {
                Event.stop(a);
                B(Event.element(a))
            },
            ".add-category": function(a) {
                Event.stop(a);
                I()
            },
            ".friend-list-page": function(a) {
                Event.stop(a);
                N(Event.element(a))
            },
            ".remove-friend": function(a) {
                Event.stop(a);
                W(Event.element(a))
            },
            ".category-limit": function(a) {
                Event.stop(a);
                Z(Event.element(a))
            },
            ".select-all": function(a) {
                Event.stop(a);
                $$("#friend-list-table input[type=checkbox]").each(function(b) {
                    b.checked = true
                })
            },
            ".deselect-all": function(a) {
                Event.stop(a);
                $$("#friend-list-table input[type=checkbox]").each(function(b) {
                    b.checked = false
                })
            },
            ".friend-move a *": function(a) {
                Event.stop(a);
                K()
            },
            ".friend-del a *": function(a) {
                Event.stop(a);
                S()
            },
            ".friendlist-search a *": function(a) {
                Event.stop(a);
                U()
            },
            "a.sort": function(a) {
                Event.stop(a);
                D(Event.element(a))
            }
        }));
        Event.observe($("friend-management-container"), "keypress", Event.delegate({
            "#friend_query": function(a) {
                if (a.keyCode == Event.KEY_RETURN) {
                    Event.stop(a);
                    U()
                }
            },
            "#category-name": function(a) {
                if (a.keyCode == Event.KEY_RETURN) {
                    Event.stop(a);
                    I()
                }
            },
            ".edit-category-name": function(a) {
                if (a.keyCode == Event.KEY_RETURN) {
                    Event.stop(a);
                    B(Event.element(a))
                }
            }
        }))
    }
});