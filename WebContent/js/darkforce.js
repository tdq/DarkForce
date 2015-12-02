var DarkForce = {
	init: function(url) {
		var self = this;
		
		this.component = $('<div>');
		$('body').append(this.component);
		
		this.socket = new WebSocket(url);
		
		this.socket.onopen = function() {
			self.socket.send(JSON.stringify({action: 'init'}));
		}
		
		this.socket.onclose = function() {
			
		}
		
		this.socket.onmessage = function(event) {
			var model = $.parseJSON(event.data);
			self.renderModel(model);
		}
	},
	
	append: function(component) {
		this.component.append(component);
	},
	
	renderModel: function(model) {
		switch(model.action) {
		case 'create': this.createComponents(model.components); break;
		case 'update': this.updateComponent(model.components); break;
		}
	},
	
	createComponents: function(components) {
		for(var i=0; i<components.length; ++i) {
			var params = components[i];
			var component = ComponentFactory.create(params);
			this.append(component.dom());
		}
	},
	
	updateComponent: function(params) {
		var component = ComponentFactory.get(params.id);
		component.update(params);
	},
	
	fireEvent: function(params) {
		var request = {action: 'event', id: params.id, event: params.type};
		this.socket.send(JSON.stringify(request));
	}
}

var ComponentFactory = {
	components: [],
	create: function(params) {
		var component;
		
		switch(params.type) {
		case 'label': component = new Label(params); break;
		case 'button': component = new Button(params); break;
		}
		
		this.components[params.id] = component;
		return component;
	},
	
	get: function(id) {
		return this.components[id];
	}
}

function Label(params) {
	this.component = $('<span>', {
		class: 'label',
		html: params.value || 'Label'
	});
}

Label.prototype.action = function(action) {
	switch(action) {
	case 'hide': this.hide(); break;
	case 'show': this.show(); break;
	}
}

Label.prototype.show = function() {
	this.component.show();
}

Label.prototype.hide = function() {
	this.component.hide();
}

Label.prototype.dom = function() {
	return this.component;
}

Label.prototype.update = function(params) {
	this.component.html(params.value);
}

function Button(params) {
	this.component = $('<button>', {
		class: 'button',
		html: params.value || 'Button'
	});
	
	this.component.click(function() {
		if(params.hasOwnProperty('bind')) {
			var component = ComponentFactory.get(params.bind.id);
			component.action(params.bind.action);
		}
		
		DarkForce.fireEvent({
			type: 'click',
			id: params.id
		});
	});
}

Button.prototype.dom = Label.prototype.dom;
