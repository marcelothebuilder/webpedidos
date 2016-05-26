/* Classes de marcação:
 * .moeda: adiciona máscara de moeda ao campo.
 */

(function(window) {
	'use strict';

	function get_lib() {
		var VendasJS = {};
		var ajaxCallbacks = [];

		VendasJS.addAjaxCallback = function(callback) {
			ajaxCallbacks.push(callback);
		};

		var _notifyAjaxCallback = function() {
			for (var i = ajaxCallbacks.length - 1; i >= 0; i--) {
				var callback = ajaxCallbacks[i];
				callback();
			}
		}

		var _init = function() {
			$(document).on("pfAjaxComplete", function() {
				_notifyAjaxCallback();
			});
		};

		_init();

		VendasJS.start = function() {
			jQuery(document).ready(function() {
				_notifyAjaxCallback();
			});
		};

		return VendasJS;
	}

	if (typeof (VendasJS) === 'undefined') {
		window.VendasJS = get_lib();
	} else {
		console.log("Não foi possível iniciar a biblioteca principal.");
	}
})(window);

VendasJS.start();


