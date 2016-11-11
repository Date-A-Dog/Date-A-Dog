$(function(){
  $('.accordion-container').each(function(){
    var thead = $(this).find('thead');
    var tbody = $(this).find('tbody');
    
    tbody.hide();
    thead.click(function(){
	  //Close any open accordion sections
  	  $(this).parent().siblings().find('tbody').each(function(){
	    $(this).slideUp();
		//$(this).changeChevron();
	  })
	  //open clicked accordion section
  	  tbody.slideToggle();
  	  changeChevron(thead);
    })
  })
});