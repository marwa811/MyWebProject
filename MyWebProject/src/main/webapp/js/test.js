require(["t5/core/ajax", "jquery"], function (ajax, $) {
	
    // Creating a callback to be invoked with <p id="result"> is clicked.
	$('#myId').click(function() {
		var dataToSend=  $('#myId').data('mydata');
		ajax('answer', {
				element: $('#myId'),
			data:{ myData:dataToSend},
			success: function(response) { 
			}
		});
	});
});
