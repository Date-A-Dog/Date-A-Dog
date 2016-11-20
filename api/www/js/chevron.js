//this find the chevron icon in the given container and switchs it to up or down depending on current state
//does not work yet
function changeChevron(){

  var glyphClass = $(this).find('indicator');
  
  if(glyphClass.hasClass("glyphicon-chevron-up")){
    glyphClass.removeClass("glyphicon-chevron-up");
	glyphClass.addClass("glyphicon-chevron-down");
  } else {
    glyphClass.removeClass("glyphicon-chevron-down")
	glyphClass.addClass("glyphicon-chevron-up");
  }
};
