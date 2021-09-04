function APIClient(endpoint) {
    this.url = endpoint;
    window._api = this;
}
APIClient.prototype = {
    constructor: APIClient,
	search:function(isbn){
		
	},
	getAll:function(callback){
		$.ajax({method:'GET',url:_api.url+'/bookstore/getAll',success:callback})
	},
	get: function(){
		
	},
	checkout:function(cartItems,coupon,callback){
	var cart = JSON.parse(cartItems);
	console.log(cart);
		$.ajax({
			url: _api.url+'/bookstore/checkout/'+coupon,
			method:'PUT',
			contentType: "application/json",
			data: cartItems,
			dataType: "text",
			success: function(response) {
				
				var jsonResp = JSON.parse(response);
				 $("#amount").html(jsonResp.totalPayableAmount);
				 $("#modal2").modal('open');
			}
		})
	},
	saveBook:function(book,callback){
		$.ajax({url:_api.url+'/bookstore/add',method:'POST',contentType: "application/json",data:JSON.stringify(book),success:callback});
	}
};