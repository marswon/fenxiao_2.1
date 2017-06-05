(function() {
	var A;
	A = (function() {
		function B() {
			this.options_index = 0;
			this.parsed = []
		}
		B.prototype.add_node = function(C) {
			if (C.nodeName === "OPTGROUP") {
				return this.add_group(C)
			} else {
				return this.add_option(C)
			}
		};
		B.prototype.add_group = function(E) {
			var G, H, I, D, C, F;
			G = this.parsed.length;
			this.parsed.push({
				array_index : G,
				group : true,
				label : E.label,
				children : 0,
				disabled : E.disabled
			});
			C = E.childNodes;
			F = [];
			for (I = 0, D = C.length; I < D; I++) {
				H = C[I];
				F.push(this.add_option(H, G, E.disabled))
			}
			return F
		};
		B.prototype.add_option = function(D, E, C) {
			if (D.nodeName === "OPTION") {
				if (D.text !== "") {
					if (E != null) {
						this.parsed[E].children += 1
					}
					this.parsed.push({
						array_index : this.parsed.length,
						options_index : this.options_index,
						value : D.value,
						text : D.text,
						html : D.innerHTML,
						selected : D.selected,
						disabled : C === true ? C : D.disabled,
						group_array_index : E,
						classes : D.className,
						style : D.style.cssText
					})
				} else {
					this.parsed.push({
						array_index : this.parsed.length,
						options_index : this.options_index,
						empty : true
					})
				}
				return this.options_index += 1
			}
		};
		return B
	})();
	A.select_to_array = function(D) {
		var F, G, E, C, B;
		G = new A();
		B = D.childNodes;
		for (E = 0, C = B.length; E < C; E++) {
			F = B[E];
			G.add_node(F)
		}
		return G.parsed
	};
	this.SelectParser = A
}).call(this);
(function() {
	var C, B;
	var A = function(D, E) {
		return function() {
			return D.apply(E, arguments)
		}
	};
	B = this;
	C = (function() {
		function D(E, F) {
			this.form_field = E;
			this.options = F != null ? F : {};
			this.set_default_values();
			this.is_multiple = this.form_field.multiple;
			this.default_text_default = this.is_multiple ? "Select Some Options"
					: "Select an Option";
			this.setup();
			this.set_up_html();
			this.register_observers();
			this.finish_setup()
		}
		D.prototype.set_default_values = function() {
			this.click_test_action = A(function(E) {
				return this.test_active_click(E)
			}, this);
			this.activate_action = A(function(E) {
				return this.activate_field(E)
			}, this);
			this.active_field = false;
			this.mouse_on_container = false;
			this.results_showing = false;
			this.result_highlighted = null;
			this.result_single_selected = null;
			this.allow_single_deselect = (this.options.allow_single_deselect != null)
					&& (this.form_field.options[0] != null)
					&& this.form_field.options[0].text === "" ? this.options.allow_single_deselect
					: false;
			this.disable_search_threshold = this.options.disable_search_threshold || 0;
			this.choices = 0;
			return this.results_none_found = this.options.no_results_text
					|| "No results match"
		};
		D.prototype.mouse_enter = function() {
			return this.mouse_on_container = true
		};
		D.prototype.mouse_leave = function() {
			return this.mouse_on_container = false
		};
		D.prototype.input_focus = function(E) {
			if (!this.active_field) {
				return setTimeout((A(function() {
					return this.container_mousedown()
				}, this)), 50)
			}
		};
		D.prototype.input_blur = function(E) {
			if (!this.mouse_on_container) {
				this.active_field = false;
				return setTimeout((A(function() {
					return this.blur_test()
				}, this)), 100)
			}
		};
		D.prototype.result_add_option = function(G) {
			var E, F;
			if (!G.disabled) {
				G.dom_id = this.container_id + "_o_" + G.array_index;
				E = G.selected && this.is_multiple ? [] : [ "active-result" ];
				if (G.selected) {
					E.push("result-selected")
				}
				if (G.group_array_index != null) {
					E.push("group-option")
				}
				if (G.classes !== "") {
					E.push(G.classes)
				}
				F = G.style.cssText !== "" ? ' style="' + G.style + '"' : "";
				return '<li id="' + G.dom_id + '" class="' + E.join(" ") + '"'
						+ F + ">" + G.html + "</li>"
			} else {
				return ""
			}
		};
		D.prototype.results_update_field = function() {
			this.result_clear_highlight();
			this.result_single_selected = null;
			return this.results_build()
		};
		D.prototype.results_toggle = function() {
			if (this.results_showing) {
				return this.results_hide()
			} else {
				return this.results_show()
			}
		};
		D.prototype.results_search = function(E) {
			if (this.results_showing) {
				return this.winnow_results()
			} else {
				return this.results_show()
			}
		};
		D.prototype.keyup_checker = function(F) {
			var G, E;
			G = (E = F.which) != null ? E : F.keyCode;
			this.search_field_scale();
			switch (G) {
			case 8:
				if (this.is_multiple && this.backstroke_length < 1
						&& this.choices > 0) {
					return this.keydown_backstroke()
				} else {
					if (!this.pending_backstroke) {
						this.result_clear_highlight();
						return this.results_search()
					}
				}
				break;
			case 13:
				F.preventDefault();
				if (this.results_showing) {
					return this.result_select(F)
				}
				break;
			case 27:
				if (this.results_showing) {
					return this.results_hide()
				}
				break;
			case 9:
			case 38:
			case 40:
			case 16:
			case 91:
			case 17:
				break;
			default:
				return this.results_search()
			}
		};
		D.prototype.generate_field_id = function() {
			var E;
			E = this.generate_random_id();
			this.form_field.id = E;
			return E
		};
		D.prototype.generate_random_char = function() {
			var G, F, E;
			G = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZ";
			E = Math.floor(Math.random() * G.length);
			return F = G.substring(E, E + 1)
		};
		return D
	})();
	B.AbstractChosen = C
}).call(this);
(function() {
	var E, D, G, C;
	var B = Object.prototype.hasOwnProperty, F = function(I, H) {
		for ( var J in H) {
			if (B.call(H, J)) {
				I[J] = H[J]
			}
		}
		function K() {
			this.constructor = I
		}
		K.prototype = H.prototype;
		I.prototype = new K;
		I.__super__ = H.prototype;
		return I
	}, A = function(H, I) {
		return function() {
			return H.apply(I, arguments)
		}
	};
	C = this;
	E = jQuery;
	E.fn
			.extend({
				chosen : function(H) {
					if (E.browser.msie
							&& (E.browser.version === "6.0" || E.browser.version === "7.0")) {
						return this
					}
					return E(this).each(function(I) {
						if (!(E(this)).hasClass("chzn-done")) {
							return new D(this, H)
						}
					})
				}
			});
	D = (function() {
		F(H, AbstractChosen);
		function H() {
			H.__super__.constructor.apply(this, arguments)
		}
		H.prototype.setup = function() {
			this.form_field_jq = E(this.form_field);
			return this.is_rtl = this.form_field_jq.hasClass("chzn-rtl")
		};
		H.prototype.finish_setup = function() {
			return this.form_field_jq.addClass("chzn-done")
		};
		H.prototype.set_up_html = function() {
			var J, I, K, L;
			this.container_id = this.form_field.id.length ? this.form_field.id
					.replace(/(:|\.)/g, "_") : this.generate_field_id();
			this.container_id += "_chzn";
			this.f_width = this.form_field_jq.outerWidth();
			this.default_text = this.form_field_jq.data("placeholder") ? this.form_field_jq
					.data("placeholder")
					: this.default_text_default;
			J = E("<div />", {
				id : this.container_id,
				"class" : "chzn-container" + (this.is_rtl ? " chzn-rtl" : ""),
				style : "width: " + this.f_width + "px;"
			});
			if (this.is_multiple) {
				J
						.html('<ul class="chzn-choices"><li class="search-field"><input type="text" value="'
								+ this.default_text
								+ '" class="default" autocomplete="off" style="width:25px;" /></li></ul><div class="chzn-drop" style="left:-9000px;"><ul class="chzn-results"></ul></div>')
			} else {
				J
						.html('<a href="javascript:void(0)" class="chzn-single"><span>'
								+ this.default_text
								+ '</span><div><b></b></div></a><div class="chzn-drop" style="left:-9000px;"><div class="chzn-search"><input type="text" autocomplete="off" /></div><ul class="chzn-results"></ul></div>')
			}
			this.form_field_jq.hide().after(J);
			this.container = E("#" + this.container_id);
			this.container.addClass("chzn-container-"
					+ (this.is_multiple ? "multi" : "single"));
			this.dropdown = this.container.find("div.chzn-drop").first();
			I = this.container.height();
			K = this.f_width - G(this.dropdown);
			this.dropdown.css({
				"width" : K + "px",
				"top" : I + "px"
			});
			this.search_field = this.container.find("input").first();
			this.search_results = this.container.find("ul.chzn-results")
					.first();
			this.search_field_scale();
			this.search_no_results = this.container.find("li.no-results")
					.first();
			if (this.is_multiple) {
				this.search_choices = this.container.find("ul.chzn-choices")
						.first();
				this.search_container = this.container.find("li.search-field")
						.first()
			} else {
				this.search_container = this.container.find("div.chzn-search")
						.first();
				this.selected_item = this.container.find(".chzn-single")
						.first();
				L = K - G(this.search_container) - G(this.search_field);
				this.search_field.css({
					"width" : L + "px"
				})
			}
			this.results_build();
			this.set_tab_index();
			return this.form_field_jq.trigger("liszt:ready", {
				chosen : this
			})
		};
		H.prototype.register_observers = function() {
			this.container.mousedown(A(function(I) {
				return this.container_mousedown(I)
			}, this));
			this.container.mouseup(A(function(I) {
				return this.container_mouseup(I)
			}, this));
			this.container.mouseenter(A(function(I) {
				return this.mouse_enter(I)
			}, this));
			this.container.mouseleave(A(function(I) {
				return this.mouse_leave(I)
			}, this));
			this.search_results.mouseup(A(function(I) {
				return this.search_results_mouseup(I)
			}, this));
			this.search_results.mouseover(A(function(I) {
				return this.search_results_mouseover(I)
			}, this));
			this.search_results.mouseout(A(function(I) {
				return this.search_results_mouseout(I)
			}, this));
			this.form_field_jq.bind("liszt:updated", A(function(I) {
				return this.results_update_field(I)
			}, this));
			this.search_field.blur(A(function(I) {
				return this.input_blur(I)
			}, this));
			this.search_field.keyup(A(function(I) {
				return this.keyup_checker(I)
			}, this));
			this.search_field.keydown(A(function(I) {
				return this.keydown_checker(I)
			}, this));
			if (this.is_multiple) {
				this.search_choices.click(A(function(I) {
					return this.choices_click(I)
				}, this));
				return this.search_field.focus(A(function(I) {
					return this.input_focus(I)
				}, this))
			}
		};
		H.prototype.search_field_disabled = function() {
			this.is_disabled = this.form_field_jq[0].disabled;
			if (this.is_disabled) {
				this.container.addClass("chzn-disabled");
				this.search_field[0].disabled = true;
				if (!this.is_multiple) {
					this.selected_item.unbind("focus", this.activate_action)
				}
				return this.close_field()
			} else {
				this.container.removeClass("chzn-disabled");
				this.search_field[0].disabled = false;
				if (!this.is_multiple) {
					return this.selected_item.bind("focus",
							this.activate_action)
				}
			}
		};
		H.prototype.container_mousedown = function(J) {
			var I;
			if (!this.is_disabled) {
				I = J != null ? (E(J.target)).hasClass("search-choice-close")
						: false;
				if (J && J.type === "mousedown") {
					J.stopPropagation()
				}
				if (!this.pending_destroy_click && !I) {
					if (!this.active_field) {
						if (this.is_multiple) {
							this.search_field.val("")
						}
						E(document).click(this.click_test_action);
						this.results_show()
					} else {
						if (!this.is_multiple
								&& J
								&& ((E(J.target)[0] === this.selected_item[0]) || E(
										J.target).parents("a.chzn-single").length)) {
							J.preventDefault();
							this.results_toggle()
						}
					}
					return this.activate_field()
				} else {
					return this.pending_destroy_click = false
				}
			}
		};
		H.prototype.container_mouseup = function(I) {
			if (I.target.nodeName === "ABBR") {
				return this.results_reset(I)
			}
		};
		H.prototype.blur_test = function(I) {
			if (!this.active_field
					&& this.container.hasClass("chzn-container-active")) {
				return this.close_field()
			}
		};
		H.prototype.close_field = function() {
			E(document).unbind("click", this.click_test_action);
			if (!this.is_multiple) {
				this.selected_item.attr("tabindex", this.search_field
						.attr("tabindex"));
				this.search_field.attr("tabindex", -1)
			}
			this.active_field = false;
			this.results_hide();
			this.container.removeClass("chzn-container-active");
			this.winnow_results_clear();
			this.clear_backstroke();
			this.show_search_field_default();
			return this.search_field_scale()
		};
		H.prototype.activate_field = function() {
			if (!this.is_multiple && !this.active_field) {
				this.search_field.attr("tabindex", this.selected_item
						.attr("tabindex"));
				this.selected_item.attr("tabindex", -1)
			}
			this.container.addClass("chzn-container-active");
			this.active_field = true;
			this.search_field.val(this.search_field.val());
			return this.search_field.focus()
		};
		H.prototype.test_active_click = function(I) {
			if (E(I.target).parents("#" + this.container_id).length) {
				return this.active_field = true
			} else {
				return this.close_field()
			}
		};
		H.prototype.results_build = function() {
			var K, M, L, J, I;
			this.parsing = true;
			this.results_data = C.SelectParser.select_to_array(this.form_field);
			if (this.is_multiple && this.choices > 0) {
				this.search_choices.find("li.search-choice").remove();
				this.choices = 0
			} else {
				if (!this.is_multiple) {
					this.selected_item.find("span").text(this.default_text);
					if (this.form_field.options.length <= this.disable_search_threshold) {
						this.container
								.addClass("chzn-container-single-nosearch")
					} else {
						this.container
								.removeClass("chzn-container-single-nosearch")
					}
				}
			}
			K = "";
			I = this.results_data;
			for (L = 0, J = I.length; L < J; L++) {
				M = I[L];
				if (M.group) {
					K += this.result_add_group(M)
				} else {
					if (!M.empty) {
						K += this.result_add_option(M);
						if (M.selected && this.is_multiple) {
							this.choice_build(M)
						} else {
							if (M.selected && !this.is_multiple) {
								this.selected_item.find("span").text(M.text);
								if (this.allow_single_deselect) {
									this.single_deselect_control_build()
								}
							}
						}
					}
				}
			}
			this.search_field_disabled();
			this.show_search_field_default();
			this.search_field_scale();
			this.search_results.html(K);
			return this.parsing = false
		};
		H.prototype.result_add_group = function(I) {
			if (!I.disabled) {
				I.dom_id = this.container_id + "_g_" + I.array_index;
				return '<li id="' + I.dom_id + '" class="group-result">'
						+ E("<div />").text(I.label).html() + "</li>"
			} else {
				return ""
			}
		};
		H.prototype.result_do_highlight = function(J) {
			var L, I, M, K, N;
			if (J.length) {
				this.result_clear_highlight();
				this.result_highlight = J;
				this.result_highlight.addClass("highlighted");
				M = parseInt(this.search_results.css("maxHeight"), 10);
				N = this.search_results.scrollTop();
				K = M + N;
				I = this.result_highlight.position().top
						+ this.search_results.scrollTop();
				L = I + this.result_highlight.outerHeight();
				if (L >= K) {
					return this.search_results.scrollTop((L - M) > 0 ? L - M
							: 0)
				} else {
					if (I < N) {
						return this.search_results.scrollTop(I)
					}
				}
			}
		};
		H.prototype.result_clear_highlight = function() {
			if (this.result_highlight) {
				this.result_highlight.removeClass("highlighted")
			}
			return this.result_highlight = null
		};
		H.prototype.results_show = function() {
			var I;
			if (!this.is_multiple) {
				this.selected_item.addClass("chzn-single-with-drop");
				if (this.result_single_selected) {
					this.result_do_highlight(this.result_single_selected)
				}
			}
			I = this.is_multiple ? this.container.height() : this.container
					.height() - 1;
			this.dropdown.css({
				"top" : I + "px",
				"left" : 0
			});
			this.results_showing = true;
			this.search_field.focus();
			this.search_field.val(this.search_field.val());
			return this.winnow_results()
		};
		H.prototype.results_hide = function() {
			if (!this.is_multiple) {
				this.selected_item.removeClass("chzn-single-with-drop")
			}
			this.result_clear_highlight();
			this.dropdown.css({
				"left" : "-9000px"
			});
			return this.results_showing = false
		};
		H.prototype.set_tab_index = function(I) {
			var J;
			if (this.form_field_jq.attr("tabindex")) {
				J = this.form_field_jq.attr("tabindex");
				this.form_field_jq.attr("tabindex", -1);
				if (this.is_multiple) {
					return this.search_field.attr("tabindex", J)
				} else {
					this.selected_item.attr("tabindex", J);
					return this.search_field.attr("tabindex", -1)
				}
			}
		};
		H.prototype.show_search_field_default = function() {
			if (this.is_multiple && this.choices < 1 && !this.active_field) {
				this.search_field.val(this.default_text);
				return this.search_field.addClass("default")
			} else {
				this.search_field.val("");
				return this.search_field.removeClass("default")
			}
		};
		H.prototype.search_results_mouseup = function(I) {
			var J;
			J = E(I.target).hasClass("active-result") ? E(I.target) : E(
					I.target).parents(".active-result").first();
			if (J.length) {
				this.result_highlight = J;
				return this.result_select(I)
			}
		};
		H.prototype.search_results_mouseover = function(I) {
			var J;
			J = E(I.target).hasClass("active-result") ? E(I.target) : E(
					I.target).parents(".active-result").first();
			if (J) {
				return this.result_do_highlight(J)
			}
		};
		H.prototype.search_results_mouseout = function(I) {
			if (E(I.target).hasClass(
					"active-result"
							|| E(I.target).parents(".active-result").first())) {
				return this.result_clear_highlight()
			}
		};
		H.prototype.choices_click = function(I) {
			I.preventDefault();
			if (this.active_field
					&& !(E(I.target).hasClass("search-choice"
							|| E(I.target).parents(".search-choice").first))
					&& !this.results_showing) {
				return this.results_show()
			}
		};
		H.prototype.choice_build = function(J) {
			var K, I;
			K = this.container_id + "_c_" + J.array_index;
			this.choices += 1;
			this.search_container
					.before('<li class="search-choice" id="'
							+ K
							+ '"><span>'
							+ J.html
							+ '</span><a href="javascript:void(0)" class="search-choice-close" rel="'
							+ J.array_index + '"></a></li>');
			I = E("#" + K).find("a").first();
			return I.click(A(function(L) {
				return this.choice_destroy_link_click(L)
			}, this))
		};
		H.prototype.choice_destroy_link_click = function(I) {
			I.preventDefault();
			if (!this.is_disabled) {
				this.pending_destroy_click = true;
				return this.choice_destroy(E(I.target))
			} else {
				return I.stopPropagation
			}
		};
		H.prototype.choice_destroy = function(I) {
			this.choices -= 1;
			this.show_search_field_default();
			if (this.is_multiple && this.choices > 0
					&& this.search_field.val().length < 1) {
				this.results_hide()
			}
			this.result_deselect(I.attr("rel"));
			return I.parents("li").first().remove()
		};
		H.prototype.results_reset = function(I) {
			this.form_field.options[0].selected = true;
			this.selected_item.find("span").text(this.default_text);
			this.show_search_field_default();
			E(I.target).remove();
			this.form_field_jq.trigger("change");
			if (this.active_field) {
				return this.results_hide()
			}
		};
		H.prototype.result_select = function(K) {
			var I, J, L, M;
			if (this.result_highlight) {
				I = this.result_highlight;
				J = I.attr("id");
				this.result_clear_highlight();
				if (this.is_multiple) {
					this.result_deactivate(I)
				} else {
					this.search_results.find(".result-selected").removeClass(
							"result-selected");
					this.result_single_selected = I
				}
				I.addClass("result-selected");
				M = J.substr(J.lastIndexOf("_") + 1);
				L = this.results_data[M];
				L.selected = true;
				this.form_field.options[L.options_index].selected = true;
				if (this.is_multiple) {
					this.choice_build(L)
				} else {
					this.selected_item.find("span").first().text(L.text);
					if (this.allow_single_deselect) {
						this.single_deselect_control_build()
					}
				}
				if (!(K.metaKey && this.is_multiple)) {
					this.results_hide()
				}
				this.search_field.val("");
				this.form_field_jq.trigger("change");
				return this.search_field_scale()
			}
		};
		H.prototype.result_activate = function(I) {
			return I.addClass("active-result")
		};
		H.prototype.result_deactivate = function(I) {
			return I.removeClass("active-result")
		};
		H.prototype.result_deselect = function(K) {
			var J, I;
			I = this.results_data[K];
			I.selected = false;
			this.form_field.options[I.options_index].selected = false;
			J = E("#" + this.container_id + "_o_" + K);
			J.removeClass("result-selected").addClass("active-result").show();
			this.result_clear_highlight();
			this.winnow_results();
			this.form_field_jq.trigger("change");
			return this.search_field_scale()
		};
		H.prototype.single_deselect_control_build = function() {
			if (this.allow_single_deselect
					&& this.selected_item.find("abbr").length < 1) {
				return this.selected_item.find("span").first().after(
						'<abbr class="search-choice-close"></abbr>')
			}
		};
		H.prototype.winnow_results = function() {
			var O, U, T, X, Y, S, V, K, R, I, J, P, M, L, Q, W, N;
			this.no_results_clear();
			K = 0;
			R = this.search_field.val() === this.default_text ? ""
					: E("<div/>").text(E.trim(this.search_field.val())).html();
			Y = new RegExp("^" + R.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, "\\$&"),
					"i");
			P = new RegExp(R.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, "\\$&"), "i");
			N = this.results_data;
			for (M = 0, Q = N.length; M < Q; M++) {
				U = N[M];
				if (!U.disabled && !U.empty) {
					if (U.group) {
						E("#" + U.dom_id).css("display", "none")
					} else {
						if (!(this.is_multiple && U.selected)) {
							O = false;
							V = U.dom_id;
							S = E("#" + V);
							if (Y.test(U.html)) {
								O = true;
								K += 1
							} else {
								if (U.html.indexOf(" ") >= 0
										|| U.html.indexOf("[") === 0) {
									X = U.html.replace(/\[|\]/g, "").split(" ");
									if (X.length) {
										for (L = 0, W = X.length; L < W; L++) {
											T = X[L];
											if (Y.test(T)) {
												O = true;
												K += 1
											}
										}
									}
								}
							}
							if (O) {
								if (R.length) {
									I = U.html.search(P);
									J = U.html.substr(0, I + R.length)
											+ "</em>"
											+ U.html.substr(I + R.length);
									J = J.substr(0, I) + "<em>" + J.substr(I)
								} else {
									J = U.html
								}
								S.html(J);
								this.result_activate(S);
								if (U.group_array_index != null) {
									E(
											"#"
													+ this.results_data[U.group_array_index].dom_id)
											.css("display", "list-item")
								}
							} else {
								if (this.result_highlight
										&& V === this.result_highlight
												.attr("id")) {
									this.result_clear_highlight()
								}
								this.result_deactivate(S)
							}
						}
					}
				}
			}
			if (K < 1 && R.length) {
				return this.no_results(R)
			} else {
				return this.winnow_results_set_highlight()
			}
		};
		H.prototype.winnow_results_clear = function() {
			var I, L, M, J, K;
			this.search_field.val("");
			L = this.search_results.find("li");
			K = [];
			for (M = 0, J = L.length; M < J; M++) {
				I = L[M];
				I = E(I);
				K
						.push(I.hasClass("group-result") ? I.css("display",
								"auto") : !this.is_multiple
								|| !I.hasClass("result-selected") ? this
								.result_activate(I) : void 0)
			}
			return K
		};
		H.prototype.winnow_results_set_highlight = function() {
			var J, I;
			if (!this.result_highlight) {
				I = !this.is_multiple ? this.search_results
						.find(".result-selected.active-result") : [];
				J = I.length ? I.first() : this.search_results.find(
						".active-result").first();
				if (J != null) {
					return this.result_do_highlight(J)
				}
			}
		};
		H.prototype.no_results = function(I) {
			var J;
			J = E('<li class="no-results">' + this.results_none_found
					+ ' "<span></span>"</li>');
			J.find("span").first().html(I);
			return this.search_results.append(J)
		};
		H.prototype.no_results_clear = function() {
			return this.search_results.find(".no-results").remove()
		};
		H.prototype.keydown_arrow = function() {
			var I, J;
			if (!this.result_highlight) {
				I = this.search_results.find("li.active-result").first();
				if (I) {
					this.result_do_highlight(E(I))
				}
			} else {
				if (this.results_showing) {
					J = this.result_highlight.nextAll("li.active-result")
							.first();
					if (J) {
						this.result_do_highlight(J)
					}
				}
			}
			if (!this.results_showing) {
				return this.results_show()
			}
		};
		H.prototype.keyup_arrow = function() {
			var I;
			if (!this.results_showing && !this.is_multiple) {
				return this.results_show()
			} else {
				if (this.result_highlight) {
					I = this.result_highlight.prevAll("li.active-result");
					if (I.length) {
						return this.result_do_highlight(I.first())
					} else {
						if (this.choices > 0) {
							this.results_hide()
						}
						return this.result_clear_highlight()
					}
				}
			}
		};
		H.prototype.keydown_backstroke = function() {
			if (this.pending_backstroke) {
				this.choice_destroy(this.pending_backstroke.find("a").first());
				return this.clear_backstroke()
			} else {
				this.pending_backstroke = this.search_container.siblings(
						"li.search-choice").last();
				return this.pending_backstroke.addClass("search-choice-focus")
			}
		};
		H.prototype.clear_backstroke = function() {
			if (this.pending_backstroke) {
				this.pending_backstroke.removeClass("search-choice-focus")
			}
			return this.pending_backstroke = null
		};
		H.prototype.keydown_checker = function(J) {
			var K, I;
			K = (I = J.which) != null ? I : J.keyCode;
			this.search_field_scale();
			if (K !== 8 && this.pending_backstroke) {
				this.clear_backstroke()
			}
			switch (K) {
			case 8:
				this.backstroke_length = this.search_field.val().length;
				break;
			case 9:
				if (this.results_showing && !this.is_multiple) {
					this.result_select(J)
				}
				this.mouse_on_container = false;
				break;
			case 13:
				J.preventDefault();
				break;
			case 38:
				J.preventDefault();
				this.keyup_arrow();
				break;
			case 40:
				this.keydown_arrow();
				break
			}
		};
		H.prototype.search_field_scale = function() {
			var N, K, Q, O, I, L, P, J, M;
			if (this.is_multiple) {
				Q = 0;
				P = 0;
				I = "position:absolute; left: -1000px; top: -1000px; display:none;";
				L = [ "font-size", "font-style", "font-weight", "font-family",
						"line-height", "text-transform", "letter-spacing" ];
				for (J = 0, M = L.length; J < M; J++) {
					O = L[J];
					I += O + ":" + this.search_field.css(O) + ";"
				}
				K = E("<div />", {
					"style" : I
				});
				K.text(this.search_field.val());
				E("body").append(K);
				P = K.width() + 25;
				K.remove();
				if (P > this.f_width - 10) {
					P = this.f_width - 10
				}
				this.search_field.css({
					"width" : P + "px"
				});
				N = this.container.height();
				return this.dropdown.css({
					"top" : N + "px"
				})
			}
		};
		H.prototype.generate_random_id = function() {
			var I;
			I = "sel" + this.generate_random_char()
					+ this.generate_random_char() + this.generate_random_char();
			while (E("#" + I).length > 0) {
				I += this.generate_random_char()
			}
			return I
		};
		return H
	})();
	G = function(H) {
		var I;
		return I = H.outerWidth() - H.width()
	};
	C.get_side_border_padding = G
}).call(this);