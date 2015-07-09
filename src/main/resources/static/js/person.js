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
    			$('#personIdInput').val(data["id"]);
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
    			$('#firstNameInput').val(data["firstName"]);
    			$('#lastNameInput').val(data["lastName"]);
				
    			var table = $('#personDisplay table').DataTable();
    			
				personTable.api().row.add(data).draw();
				
				var index = table.rows().eq(0).filter(function(index){
					return table.row(index).data()["id"] == data["id"] ? true : false;
				});
				
    			$('#personDisplay table .selected').removeClass('selected');
    			table.rows(index).nodes().to$().addClass('selected');
				
			}
		});
	});
	
	$('#personUpdate').click(function(){
		
		person = {
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
					
					var index = table.rows('.selected')[0][0];
					
					table.row(index).data(data);
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
					
					var index = table.rows('.selected')[0][0];
														
	    			table.rows('.selected').remove().draw();
	    			
	    			table.row(index).nodes().to$().addClass('selected');
	    			
	    			var data = table.row(index).data();

	    			$('#personIdInput').val(data["id"]);
	    			$('#firstNameInput').val(data["firstName"]);
	    			$('#lastNameInput').val(data["lastName"]);
	    			
				}
			});
			
		}
		
	});
	
});