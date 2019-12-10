$.extend({
    createUploadIframe: function(id){
        var frameId = 'jUploadFrame' + id;
        return $('<iframe></iframe>').attr({id:frameId,name:frameId}).css({top:'-9999px',left:'-9999px',position:'absolute'}).appendTo(document.body)[0];
    },
    createUploadForm: function(id, fileElementId){
        var formId = 'jUploadForm' + id,fileId = 'jUploadFile' + id,oldElement = $('#' + fileElementId),newElement = $(oldElement).clone();
        return $('<form></form>').attr({id:formId,name:formId}).append($(oldElement).attr('id', fileId).before(newElement)).css({position:'absolute',top:'-1200px',left:'-1200px'}).appendTo('body')[0];
    },
    addOtherRequestsToForm: function(form,data){
        var originalElement = $('<input type="hidden"/>');
        for (var key in data) originalElement.clone().attr({'name':key,'value':data[key]}).appendTo(form);
        return form;
    },
    ajaxFileUpload: function(s) {
        s = $.extend({}, $.ajaxSettings, s);
        var id = new Date().getTime(),io = $.createUploadIframe(id),form = $.createUploadForm(id, s.fileElementId),frameId = 'jUploadFrame' + id,formId = 'jUploadForm' + id,requestDone = false,xml = {};
        if ( s.data ) form = $.addOtherRequestsToForm(form,s.data);
        if ( s.global && ! $.active++ ) $.event.trigger( "ajaxStart" );
        if ( s.global ) $.event.trigger("ajaxSend", [xml, s]);
        var uploadCallback = function(isTimeout){
            var io = $('#'+frameId)[0];
            try{
            	var ioContent=io.contentWindow||io.contentDocument;
                if(ioContent)xml.responseText = ioContent.document.body?ioContent.document.body.innerHTML:null,xml.responseXML = ioContent.document.XMLDocument?ioContent.document.XMLDocument:ioContent.document;
            }catch(e){
                $.handleError(s, xml, null, e);
            }
            if ( xml || isTimeout == "timeout"){
                requestDone = true;
                var status;
                try {
                    status = isTimeout != "timeout" ? "success" : "error";
                    if ( status != "error" ){
                        var data = $.uploadHttpData( xml, s.dataType );
                        if ( s.success ) s.success( data, status );
                        if( s.global )$.event.trigger( "ajaxSuccess", [xml, s] );
                    } else
                        $.handleError(s, xml, status);
                } catch(e){
                    status = "error";
                    $.handleError(s, xml, status, e);
                }
                if( s.global )
                    $.event.trigger( "ajaxComplete", [xml, s] );
                if ( s.global && ! --$.active )
                    $.event.trigger( "ajaxStop" );
                if ( s.complete )
                    s.complete(xml, status);
                $(io).off();
                setTimeout(function(){
	                try{
		                $(io,form).remove();
	                } catch(e){
                        $.handleError(s, xml, null, e);
                    }
                }, 100);
                xml = null;
            }
        };
        if ( s.timeout > 0 )
            setTimeout(function(){if( !requestDone ) uploadCallback( "timeout" );}, s.timeout);
        try{
            $('#' + formId).attr({action:s.url,method:'POST',target:frameId,encoding:'multipart/form-data',enctype:'multipart/form-data'}).submit();
        } catch(e){
            $.handleError(s, xml, null, e);
        }
        $('#'+frameId).on('load',uploadCallback);
        return {abort: function () {}};
    },
    uploadHttpData: function( r, type ) {
        var data = !type;
        data = type == "xml" || data ? r.responseXML : r.responseText;
        if ( type == "script" )
            $.globalEval( data );
        if ( type == "json" ){
        	var reg=/<pre.*?>(.*?)<\/pre>/i;
        	if(reg.test(data)){
                var am = reg.exec(data);
                data =am? am[1] : '';
        	}
            data=$.parseJSON(data);
        }
        if ( type == "html" )
            $("<div>").html(data).evalScripts();
        return data;
    },
    handleError: function( s, xhr, status, e ){
        if ( s.error )
            s.error.call( s.context || s, xhr, status, e );
        if ( s.global )
            (s.context ? $(s.context) : $.event).trigger( 'ajaxError', [xhr, s, e] );
    }
})