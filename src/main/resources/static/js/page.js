$(function(){
	$('body').tabs({
		active: 0,
		activate: function(event, ui) {
			$(ui["newPanel"]).css("display", "flex");
			personTable.fnAdjustColumnSizing();
			familyTable.fnAdjustColumnSizing();
			familyAddMemberTable.fnAdjustColumnSizing();
			familyMembersTable.fnAdjustColumnSizing();
	    }
	});
});