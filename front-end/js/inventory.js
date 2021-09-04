function InventoryManager(endpoint) {
    this.api = new APIClient(endpoint);
	window._inventoryManager = null;
    window._inventoryManager = this;
}
InventoryManager.prototype = {
constructor: InventoryManager,
	loadBooks: function(){
		_inventoryManager.api.getAll(_inventoryManager.fillBooks);
	},
	fillBooks:function(books){
		var book_card = '<div class="col s12 m3 l3"><div class="card" id="{autoGenId}"><div class="card-image"><img class="activator img-gradient" src="{imageUrl}"><span class="card-title bookName">{bookTitle}</span><a onclick="addToCart(\'{autoGenId_}\')" class="btn-floating halfway-fab waves-effect waves-light red"><i class="material-icons">local_grocery_store</i></a></div><div class="card-content"><p class="authorName">{authorName}</p><p class="isbnNum">{isbnNum}</p><p class="price">{price}</p></div><div class="card-reveal"><span class="card-title grey-text text-darken-4">{bookTitle}<i class="material-icons right">close</i></span><pclass="desc">{bookDesc}</p></div></div></div>';
		var row = '<div class="row">';
		var i = 0;
		books.forEach(function(item){
			if(!item.image){
				item.image = 'img/cover-not-available.png';
			}
			var book = book_card.replace('{imageUrl}',item.image).replace('{bookTitle}',item.name).replace('{price}',item.price+' AED').replace('{authorName}',item.author).replace('{bookDesc}',item.description).replace('{isbnNum}',item.isbn).replace('{autoGenId}','book_'+i).replace('{autoGenId_}','book_'+i);
			row+=book;
			i++;
		});
		row+='</div>';
		$(row).appendTo('.container');
		$('#empty').hide();
		},
	saveBook:function(book,callback){
		_inventoryManager.api.saveBook(book,callback);
	}
	,
	getBooks:function(){
		_inventoryManager.api.getAll(_inventoryManager.fillBooks)
	},
	fillBooks:function(books){
		 if(books) {
			$('#empty').hide();
		 }
		var book_card = '<div class="col s12 m3 l3"><div class="card" id="{autoGenId}"><div class="card-image"><img class="activator img-gradient" src="{imageUrl}"><span class="card-title bookName">{bookTitle}</span><a onclick="addToCart(\'{autoGenId_}\')" class="btn-floating halfway-fab waves-effect waves-light red"><i class="material-icons">local_grocery_store</i></a></div><div class="card-content"><p class="authorName">{authorName}</p><p class="isbnNum">{isbnNum}</p><p class="price">{price}</p></div><div class="card-reveal"><span class="card-title grey-text text-darken-4">{bookTitle}<i class="material-icons right">close</i></span><pclass="desc">{bookDesc}</p></div></div></div>';
		var row = '<div class="row">';
		var i = 0;
		books.forEach(function(item){
			if(!item.image){
				item.image = 'img/cover-not-available.png';
			}
			var book = book_card.replace('{imageUrl}',item.image).replace('{bookTitle}',item.name).replace('{price}',item.price+' AED').replace('{authorName}',item.author).replace('{bookDesc}',item.description).replace('{isbnNum}',item.isbn).replace('{autoGenId}','book_'+i).replace('{autoGenId_}','book_'+i);
			row+=book;
			i++;
		});
		row+='</div>';
		$(row).appendTo('.container');
	},
};