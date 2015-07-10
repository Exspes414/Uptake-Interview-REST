var personTable;

$(window).ready(function(){
		
	// Initialize the Person table and add the handler for clicking on rows
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
    			
    			// Change which row is highlighted
    			$('#personDisplay table .selected').removeClass('selected');
    			$(row).addClass('selected');
    			
    			// Set the edit data
    			$('#personIdInput').val(data["id"]);
    			$('#firstNameInput').val(data["firstName"]);
    			$('#lastNameInput').val(data["lastName"]);
    		});
    	}
	});
	
	$(window).resize(function(){
		personTable.fnAdjustColumnSizing();
	});
	
	// Get the initial data
	$.get("/person", function(data){
		personTable.api().rows.add(data).draw();
	}).fail(function(){
		// Display error
	});
	
	// Add button Handlers
	
	
	$('#personCreate').click(function(){
		
		person = {
				"firstName": $('#firstNameInput').val(),
				"lastName": $('#lastNameInput').val()
		};
		
		$.ajax({
			type: "POST",
			url: "/person",
			data: JSON.stringify(person),
			contentType: "application/json",
			success: function(data){
				
    			$('#personIdInput').val(data["id"]);
				
    			var table = $('#personDisplay table').DataTable();
    			
				table.row.add(data).draw();
				
				// Set the new row as the selected row
				var index = table.rows().eq(0).filter(function(index){
					return table.row(index).data()["id"] == data["id"] ? true : false;
				});
				
    			$('#personDisplay table .selected').removeClass('selected');
    			table.rows(index).nodes().to$().addClass('selected');
				    			
    			$('#familyDisplay .selected').click();
			}
		});
	});
	
	$('#personUpdate').click(function(){
		
		var person = {
				"id": $('#personIdInput').val(),
				"firstName": $('#firstNameInput').val(),
				"lastName": $('#lastNameInput').val()
		};
		
		if( person["id"] != ""){
		
			$.ajax({
				url: "/person/" + person["id"],
				type: "PUT",
				data: JSON.stringify(person),
				contentType: "application/json",
				success: function(data){

					var table = $('#personDisplay table').DataTable();
					
					// Update the data in the table
					var index = table.rows('.selected')[0][0];
					
					table.row(index).data(data);

					$('#familyDisplay .selected').click();
				}
			});
			
		}
		
	});
	
	$('#personDelete').click(function(){

		person = {
				"id": $('#personIdInput').val(),
				"firstName": $('#firstNameInput').val(),
				"lastName": $('#lastNameInput').val()
		};
		
		if( person["id"] != ""){
			
			$.ajax({
				url: "/person/" + person["id"],
				type: "DELETE",
				success: function(){
					var table = $('#personDisplay table').DataTable();
					
					// Remove the selected row and select the next row (which will now be where the deleted row was)
					var index = table.rows('.selected')[0][0];
																								
	    			table.rows('.selected').remove().draw();
	    			
	    			table.row(index).nodes().to$().addClass('selected');
	    			
	    			var data = table.row(index).data();

	    			$('#personIdInput').val(data["id"]);
	    			$('#firstNameInput').val(data["firstName"]);
	    			$('#lastNameInput').val(data["lastName"]);

	    			$('#familyDisplay .selected').click();
	    			
				}
			});
			
		}
		
	});
	
});