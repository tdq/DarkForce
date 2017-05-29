/**
 * DarkForce function
 */
var DarkForce = {
	init : function(config) {
		var self = this;

		this.component = document.querySelector('body');
		self.renderModel(config);
	},

	append : function(component) {
		this.component.append(component);
	},

	renderModel : function(model) {
		switch (model.action) {
		case 'create':
			this.createComponents(model.components);
			break;
		case 'update':
			this.updateComponent(model.components);
			break;
		default:
			this.createComponents(model.components);
		}
	},

	createComponents : function(components) {
		for (var i = 0; i < components.length; ++i) {
			var params = components[i];
			var component = ComponentFactory.create(params);
			this.append(component.dom());
		}
	},

	updateComponent : function(params) {
		var component = ComponentFactory.get(params.id);
		if (component) {
			component.update(params);
		}
	},

	fireEvent : function(params) {
		var request = {
			action : 'event',
			id : params.id,
			event : params.type,
			value : params.value
		};

		// this.socket.send(JSON.stringify(request));
	}
};

/**
 * Component factory stores components and creates new ones. For registering new
 * component constructor call ComponentFactory.register(type, function(params) {
 * return new Component(params); });
 */
var ComponentFactory = {
	types : [],
	components : [],
	create : function(params) {
		var component;
		var constructor;

		if (params.hasOwnProperty('type')) {
			constructor = this.types[params.type];
		} else {
			constructor = this.types[this.readType(params.id)];
		}

		if (constructor) {
			component = constructor(params);
		}

		this.components[params.id] = component;
		return component;
	},

	get : function(id) {
		return this.components[id];
	},

	register : function(type, constructor) {
		this.types[type] = constructor;
	},
	
	readType : function(id) {
		return id.substr(0, id.indexOf('_'));
	}
};

/**
 * Extend parent class by child class
 * 
 * @param Child
 * @param Parent
 */
function extend(Child, Parent) {
	var F = function() {
	};
	F.prototype = Parent.prototype;
	var f = new F();

	for ( var prop in Child.prototype)
		f[prop] = Child.prototype[prop];
	Child.prototype = f;
	Child.prototype.super = Parent.prototype;
}

/**
 * Abstract component
 * 
 * @param params
 */
function Component(params) {
	this.component = document.createElement('div');
}

Component.prototype = {
	constructor : Component,

	show : function() {
		this.component.show();
	},

	hide : function() {
		this.component.hide();
	},

	action : function(action, params) {
		params = params || null;
	},

	dom : function() {
		return this.component;
	},

	update : function(params) {
	}
};

/**
 * Label section
 */
function Label(params) {
	this.component = document.createElement('df-label');
	this.component.innerHTML = params.value || 'Label';
}

extend(Label, Component);

Label.prototype.action = function(action, params) {
	switch (action) {
	case 'hide':
		this.hide();
		break;
	case 'show':
		this.show();
		break;
	case 'setValue':
		this.setValue(params);
		break;
	}
};

Label.prototype.update = function(params) {
	this.component.innreHTML = params.value;
};

Label.prototype.setValue = function(value) {
	this.component.innerHTML = value;
}

ComponentFactory.register('label', function(params) {
	return new Label(params);
});

// =====================================================================

/**
 * Button section
 */
function Button(params) {
	this.component = document.createElement('button');
	this.component.setAttribute('class', 'button glass');
	this.component.innerHTML = params.value || 'Button';

	this.component.addEventListener('click', function() {
		if (params.hasOwnProperty('bind')) {
			var component = ComponentFactory.get(params.bind.id);

			if (params.bind.hasOwnProperty('params')) {
				component.action(params.bind.action, params.bind.params);
			} else {
				component.action(params.bind.action);
			}
		}

		/*
		 * DarkForce.fireEvent({ type : 'click', id : params.id });
		 */
	});
}

extend(Button, Component);

ComponentFactory.register('button', function(params) {
	return new Button(params);
});

// ======================================================================

/**
 * Vertical layout section
 */
function Vertical(params) {
	this.compIds = [];

	this.component = document.createElement('df-vertical');

	for (var i = 0; i < params.components.length; ++i) {
		var element = params.components[i];
		var comp = ComponentFactory.create(element);
		var cell = document.createElement('df-cell');
		cell.append(comp.dom());
		this.component.append(cell);
		this.compIds[element.id] = true;
	}
}

extend(Vertical, Component);

Vertical.prototype.update = function(params) {
	for (var i = 0; i < params.components.length; ++i) {
		var element = params.components[i];

		if (this.compIds[element.id]) {
			// Element exists
		} else {
			// Element is new
			var comp = ComponentFactory.create(element);
			var cell = document.createElement('df-cell');
			cell.append(comp.dom());
			this.component.append(cell);
			this.compIds[element.id] = true;
		}
	}
};

ComponentFactory.register('vertical', function(params) {
	return new Vertical(params);
});

// =============================================================

/**
 * Horizontal layout section
 */
function Horizontal(params) {
	this.compIds = [];

	this.component = document.createElement('df-horizontal');

	for (var i = 0; i < params.components.length; ++i) {
		var element = params.components[i];
		var comp = ComponentFactory.create(element);
		var cell = document.createElement('df-cell');
		cell.append(comp.dom());
		this.component.append(cell);
		this.compIds[element.id] = true;
	}
}

extend(Horizontal, Vertical);

ComponentFactory.register('horizontal', function(params) {
	return new Horizontal(params);
});

// =============================================================

/**
 * InputText component section
 */
function InputText(params) {

	var self = this;
	this.component = document.createElement('input')
	this.component.setAttribute('type', 'text');
	this.component.setAttribute('class', 'glass');

	if (params.hasOwnProperty('value')) {
		this.component.setAttribute('value', params.value);
	}

	if (params.hasOwnProperty('placeholder')) {
		this.component.setAttribute('placeholder', params.placeholder);
	}

	this.component.addEventListener('keyup', function() {
		if (params.hasOwnProperty('bind')) {
			var component = ComponentFactory.get(params.bind.id);

			// if (params.bind.hasOwnProperty('params')) {
			component.action(params.bind.action, self.component.value);
			// } else {
			// component.action(params.bind.action);
			// }
		}

		/*
		 * DarkForce.fireEvent({ type : 'change', id : params.id, value :
		 * $(this).val() });
		 */
	});
}

extend(InputText, Component);

InputText.prototype.action = function(action, params) {
	switch (action) {
	case 'hide':
		this.hide();
		break;
	case 'show':
		this.show();
		break;
	case 'clear':
		this.clearValue();
		break;
	}
}

InputText.prototype.clearValue = function() {
	this.component.value = null;
}

ComponentFactory.register('inputText', function(params) {
	return new InputText(params);
});

// =============================================================

/**
 * VaadinUpload component section
 */
function VaadinUpload(params) {
	this.component = document.createElement('vaadin-upload');
}

extend(VaadinUpload, Component);

ComponentFactory.register('vaadinUpload', function(params) {
	return new VaadinUpload(params);
});

// =============================================================

/**
 * Vaadin-grid section
 */
function VaadinGrid(params) {
	this.component = document.createElement('vaadin-grid');

	// Only for testing
	this.component.innerHTML = '<table>' + '<colgroup>'
			+ '<col name="column1"/>' + '<col name="column2"/>'
			+ '<col name="column3"/>' + '</colgroup>' + '</table>';
}

extend(VaadinGrid, Component);
/*
 * VaadinGrid.prototype.action = function(action) { switch(action) { case
 * 'hide': this.hide(); break; case 'show': this.show(); break; case 'update':
 * this.update(null); break; } };
 * 
 * VaadinGrid.prototype.update = function(params) { // Only for testing
 * this.component.items = [ {column1:"value1", column2:"value2",
 * column3:"value3"} ]; };
 */
ComponentFactory.register('vaadinGrid', function(params) {
	return new VaadinGrid(params);
});

// =====================================================================
