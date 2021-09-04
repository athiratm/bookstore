function CartManager(endpoint) {
    this.api = new APIClient(endpoint);
	window._cartManager = null;
    window._cartManager = this;
}
CartManager.prototype = {
constructor: CartManager,
	/**
	* Search the book using book isbn number
	* @param {isbn} string
	**/
	loadBooks: function(){
		var cartJson = localStorage.getItem('cart');
		cartItems = JSON.parse(cartJson);
		if(cartItems){
		_cartManager.fillBooks(cartItems)
		}
	},
	fillBooks:function(books){
		var row='<div class="row">';
		var summary = '';
		var total = 0;
		var rowItem = '<div id="{isbn}" class="col s12 m7"><div class="card horizontal"><div class="card-image"><img src="{imageUrl}"></div><div class="card-stacked"><div class="card-content"><p>{bookName}</p><p class="price">{price}</p></div><div class="card-action"><a onclick="removeItem(\'{isbn_}\')" href="#">Remove</a></div></div></div></div>';
		books.forEach(function(item){
			summary+='<p data-price="'+item.price+'" id="summary_'+item.isbn+'">'+item.name+' x 1 = '+item.price+'</p>';
			total+=item.price;
		    row+= rowItem.replace('{imageUrl}',item.image).replace('{isbn}',item.isbn).replace('{bookName}',item.name).replace('{price}',item.price+' AED').replace('{isbn_}',item.isbn);
		});
		row+='</div>';
		$(summary).appendTo('#items');
		$(row).appendTo('.cart-items');
		$('#total-price').text(total+' AED');
		if(total<=0){
			$('#checkout_button').addClass('disabled')
		}
	},
	checkout:function(cartjson,coupon) {
		_cartManager.api.checkout(cartjson,coupon,function(){
			alert('Checkout complete')
		});
		
	}
};