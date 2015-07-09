var personData = [];

var personTable;

$(window).ready(function(){
		
	personTable = $('#personDisplay table').dataTable({
		"sScrollY": ($('#personDisplay').height() - 200),
		"bPaginate": false,
  		"bJQueryUI": true,
  		"autoWidth": false,
  		"aoColumns": [
  		            {"mData": "firstName"},
  		            {"mData": "lastName"}
	            ],
    	"createdRow": function(row, data, dataIndex){
    		$(row).click(function(){
    			$('#personDisplay table .selected').removeClass('selected');
    			$(row).addClass('selected');
    			$('#personIdInput').val(data["idName"]);
    			$('#firstNameInput').val(data["firstName"]);
    			$('#lastNameInput').val(data["lastName"]);
    		});
    	}
	});
	
	$(window).resize(function(){
		personTable.fnAdjustColumnSizing();
	});
	
	personTable.api().rows.add(personData).draw();
	
	$.get("/person", function(data){
		personData = data;
		personTable.api().rows.add(personData).draw();
	}).fail(function(){
		// Display error
	});
	
	
	
	
});