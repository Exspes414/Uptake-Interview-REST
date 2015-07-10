var familyData = [];
var nonFamilyMembersData = [];
	
var familyTable, familyAddMemberTable, familyMembersTable;

$(window).ready(function(){
		
	// Setup the table that lists the families
	familyTable = $('#familyDisplay table').dataTable({
		"sScrollY": ($('#personDisplay').height() - 200),
		"bPaginate": false,
  		"bJQueryUI": true,
  		"autoWidth": false,
  		"aoColumns": [
  		            {"mDataProp": "name"},
	            ],
    	"createdRow": function(row, data, dataIndex){
    		    		
    		// Add a click handler to each row
    		$(row).click(function(){
    			
    			// Change row highlight
    			$('#familyDisplay table .selected').removeClass('selected');
    			$(row).addClass('selected');
    			
    			// Change displayed data
    			$('#familyIdInput').val(data["id"]);
    			$('#familyNameInput').val(data["name"]);
    			
    			familyMembersData = data["members"];
    			
    			familyMembersTable.api().clear();
    			familyMembersTable.api().rows.add(familyMembersData).draw();
    			
    			familyAddMemberTable.api().clear();
    			
    			var memberIds = [];
    			
    			$.each(data["members"], function(index, value){
    				memberIds.push(value["id"]);
    			});
    			
    			nonFamilyMembersData = personTable.api().data().filter( function(element){
    				return memberIds.indexOf(element["id"]) < 0;
    			});
    			
    			familyAddMemberTable.api().rows.add(nonFamilyMembersData).draw();
    			
    		});
    	}
	});
	
	// create the table used to add members to a family
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
    		
    		// Add row click handler
    		$(row).click(function(){
    			
    			// Change the selected row
    			$('#familyAddMember table .selected').removeClass('selected');
    			$(row).addClass('selected');
    			
    		});
    	}
	});
	
	// create the table used to show/remove members from a family
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
    		
    		// Add a click handler
    		$(row).click(function(){
    			
    			// change the selected row
    			$('#familyMembersTable table .selected').removeClass('selected');
    			$(row).addClass('selected');
    		});
    	}
	});
	
	$(window).resize(function(){
		familyTable.fnAdjustColumnSizing();
		familyAddMemberTable.fnAdjustColumnSizing();
		familyMembersTable.fnAdjustColumnSizing();
	});

	// Get initial data
	$.get("/family", function(data){
		familyData = data;
		familyTable.api().rows.add(familyData).draw();
	}).fail(function(){
		// Display error
	});
	
	// Add button handlers
	
	$("#familyCreate").click(function(){
		
		if( $('#familyNameInput').val() ){
			
			var family = {
					"name": $('#familyNameInput').val()					
			}		
			
			$.ajax({
				url: "/family",
				method: "POST",
				data: JSON.stringify(family),
				contentType: "application/json",
				success: function(data){

					$('#familyIdInput').val(data["id"]);

	    			var table = $('#familyDisplay table').DataTable();
					
					table.row.add(data).draw();
					
					// Set the new row as the selected row
					var index = table.rows().eq(0).filter(function(index){
						return table.row(index).data()["id"] == data["id"] ? true : false;
					});
					
					$('#familyDisplay table .selected').removeClass('selected');
					table.rows(index).nodes().to$().addClass('selected');
					
				}
			});
			
		}
		
	});

	$("#familyUpdate").click(function(){

		if( $('#familyNameInput').val() && $('#familyIdInput').val()){
			
			var family = {
					"id": $('#familyIdInput').val(),
					"name": $('#familyNameInput').val()					
			}		
			
			$.ajax({
				url: "/family/" + $('#familyIdInput').val(),
				method: "PUT",
				data: JSON.stringify(family),
				contentType: "application/json",
				success: function(data){

	    			var table = $('#familyDisplay table').DataTable();

	    			// Update the data in the table
	    			var index = table.rows('.selected')[0][0];
	    			
	    			table.row(index).data(data);
					
				}
			});
			
		}
	});

	$("#familyDelete").click(function(){

		if( $('#familyNameInput').val() ){
			
			var family = {
					"id": $('#familyIdInput').val(),
					"name": $('#familyNameInput').val()					
			}		
			
			$.ajax({
				url: "/family/" + $('#familyIdInput').val(),
				method: "DELETE",
				success: function(data){

	    			var table = $('#familyDisplay table').DataTable();

	    			// Remove the selcted row and select the next row
					var index = table.rows('.selected')[0][0];
					
					table.rows('.selected').remove().draw();
					
					table.row(index-1).nodes().to$().addClass('selected');
					
					var data = table.row(index-1).data();
					
					$('#familyIdInput').val(data['id']);
					$('#familyNameInput').val(data['name']);
					
				}
			});
			
		}
	});
	
	$('#familyMemberAdd').click(function(){
		
		var memberData = familyAddMemberTable.api().row('.selected').data();
		
		if( memberData != null ){
			
			$.ajax({
				url: "/family/" + $('#familyIdInput').val() + "/members/" + memberData["id"],
				method: "POST",
				data: JSON.stringify(memberData),
				contentType: "application/json",
				success: function(data){
					
					// Move the Person to the Family members table
					var index = familyAddMemberTable.api().rows('.selected')[0][0];
					
					familyAddMemberTable.api().rows('.selected').remove().draw();
					
					familyAddMemberTable.api().row(index).nodes().to$().addClass('selected');
										
					familyMembersTable.api().rows.add([memberData]).draw();
				}
			});
		}
	});
	
	$('#familyMemberRemove').click(function(){
		
		var memberData = familyMembersTable.api().row('.selected').data();
		
		if( memberData != null ){
			
			$.ajax({
				url: "/family/" + $('#familyIdInput').val() + "/members/" + memberData["id"],
				method: "DELETE",
				success: function(data){
					
					// Move the Person to the not family members table
					var index = familyMembersTable.api().rows('.selected')[0][0];
					
					familyMembersTable.api().rows('.selected').remove().draw();
					
					familyMembersTable.api().row(index).nodes().to$().addClass('selected');
										
					familyAddMemberTable.api().rows.add([memberData]).draw();
				}
			});
			
		}
		
	});
	
		
	
});