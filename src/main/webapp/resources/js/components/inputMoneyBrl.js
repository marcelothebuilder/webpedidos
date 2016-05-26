/**
 * 
 */
function maskMoneyComponent(component) {
	$(component).maskMoney({
		decimal : ',',
		thousand : '.',
		showSymbol : true,
		precision: 2,
		prefix : "R$ ",
		allowZero : true
	});
}