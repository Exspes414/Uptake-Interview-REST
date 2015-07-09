var personData = [
	                  {
	                	  'id': 1,
	                	  'firstName': 'Jacob',
	                	  'lastName': 'Williams'
	                  },
	                  {
	                	  'id': 2,
	                	  'firstName': 'Marie',
	                	  'lastName': 'Williams'
	                  },
	                  {
	                	  'id': 3,
	                	  'firstName': 'Steve',
	                	  'lastName': 'Williams'
	                  },
	                  {
	                	  'id': 4,
	                	  'firstName': 'Mikey',
	                	  'lastName': 'Williams'
	                  },
	                  {
	                	  'id': 5,
	                	  'firstName': 'Michelle',
	                	  'lastName': 'Williams'
	                  },
	                  {
	                	  'id': 6,
	                	  'firstName': 'Joe',
	                	  'lastName': 'Smith'
	                  },
	                  {
	                	  'id': 7,
	                	  'firstName': 'Jane',
	                	  'lastName': 'Smith'
	                  },
	                  {
	                	  'id': 8,
	                	  'firstName': 'Another',
	                	  'lastName': 'Person'
	                  },
                  ];

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
    			$('#idInput').val(data["idName"]);
    			$('#firstNameInput').val(data["firstName"]);
    			$('#lastNameInput').val(data["lastName"]);
    		});
    	}
	});
	
	$(window).resize(function(){
		personTable.fnAdjustColumnSizing();
	});
	
	personTable.api().rows.add(personData).draw();
	
	/*
	$.get("/person", function(data){
		personData = data;
	}).fail(function(){
		// Display error
	});
	*/
	
	
});