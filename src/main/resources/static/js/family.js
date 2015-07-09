var familyData = [];

var familyTable, familyAddMemberTable, familyMembersTable;

$(window).ready(function(){
		
	familyTable = $('#familyDisplay table').dataTable({
		"sScrollY": ($('#personDisplay').height() - 200),
		"bPaginate": false,
  		"bJQueryUI": true,
  		"autoWidth": false,
  		"aoColumns": [
  		            {"mDataProp": "name"},
	            ],
    	"createdRow": function(row, data, dataIndex){
    		    		
    		$(row).click(function(){
    			$('#familyDisplay table .selected').removeClass('selected');
    			$(row).addClass('selected');
    			$('#familyIdInput').val(data["id"]);
    			$('#familyNameInput').val(data["name"]);
    			
    			
    			
    		});
    	}
	});
	
	familyAddMemberTable = $('#familyAddMember table').dataTable({
		"sScrollY": 400,
		"bPaginate": false,
  		"bJQueryUI": true,
  		"autoWidth": false,
  		"aoColumns": [
  		            {"mData": "firstName"},
  		            {"mData": "lastName"}
	            ],
    	"createdRow": function(row, data, dataIndex){
    		$(row).click(function(){
    			$('#familyAddMember table .selected').removeClass('selected');
    			$(row).addClass('selected');
    			$('#personIdInput').val(data["idName"]);
    			$('#firstNameInput').val(data["firstName"]);
    			$('#lastNameInput').val(data["lastName"]);
    		});
    	}
	});
	
	familyMembersTable = $('#familyMembersTable table').dataTable({
		"sScrollY": 400,
		"bPaginate": false,
  		"bJQueryUI": true,
  		"autoWidth": false,
  		"aoColumns": [
  		            {"mData": "firstName"},
  		            {"mData": "lastName"}
	            ],
    	"createdRow": function(row, data, dataIndex){
    		$(row).click(function(){
    			$('#familyAddMember table .selected').removeClass('selected');
    			$(row).addClass('selected');
    			$('#personIdInput').val(data["idName"]);
    			$('#firstNameInput').val(data["firstName"]);
    			$('#lastNameInput').val(data["lastName"]);
    		});
    	}
	});
	
	$(window).resize(function(){
		familyTable.fnAdjustColumnSizing();
		familyAddMemberTable.fnAdjustColumnSizing();
		familyMembersTable.fnAdjustColumnSizing();
	});

	$.get("/family", function(data){
		familyData = data;
		familyTable.api().rows.add(familyData).draw();
	}).fail(function(){
		// Display error
	});
	
		
	
});