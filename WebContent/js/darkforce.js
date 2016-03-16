/**
 * DarkForce function
 */
var DarkForce = {
    init: function(url) {
        var self = this;

        this.component = $('body');

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
        if(component) {
            component.update(params);
        }
    },

    fireEvent: function(params) {
        var request = {action: 'event', id: params.id, event: params.type, value: params.value};
        this.socket.send(JSON.stringify(request));
    }
}

/**
 * Component factory stores components and creates new ones.
 * For registering new component constructor call ComponentFactory.register(type, function(params) {
 * 	return new Component(params);
 * });
 */
var ComponentFactory = {
    types: [],
    components: [],
    create: function(params) {
        var component;
        /*
        switch(params.type) {
        case 'label': component = new Label(params); break;
        case 'button': component = new Button(params); break;
        case 'vertical': component = new Vertical(params); break;
        }
        */

        var constructor = this.types[params.type];
        if(constructor) {
            component = constructor(params);
        }

        this.components[params.id] = component;
        return component;
    },

    get: function(id) {
        return this.components[id];
    },

    register: function(type, constructor) {
        this.types[type] = constructor;
    }
}

/**
 * Extend parent class by child class
 * @param Child
 * @param Parent
 */
function extend(Child, Parent) {
    var F = function () { };
    F.prototype = Parent.prototype;
    var f = new F();
    
    for (var prop in Child.prototype) f[prop] = Child.prototype[prop];
    Child.prototype = f;
    Child.prototype.super = Parent.prototype;
}

/**
 * Abstract component
 * @param params
 */
function Component(params) {
    this.component = $('<div>');
}

Component.prototype = {
    constructor: Component,

    show : function() {
        this.component.show();
    },

    hide : function() {
        this.component.hide();
    },

    action: function(action) {},

    dom : function() {
        return this.component;
    },

    update : function(params) {}
}

/**
 * Label section
 */
function Label(params) {	
    this.component = $('<df-label>');	
    this.component.html(params.value || 'Label');
}

extend(Label, Component);

Label.prototype.action = function(action) {
    switch(action) {
    case 'hide': this.hide(); break;
    case 'show': this.show(); break;
    }
}

Label.prototype.update = function(params) {
    this.component.html(params.value);
}

ComponentFactory.register('label', function(params) {
    return new Label(params);
});

//=====================================================================

/**
 * Button section
 */
function Button(params) {	
    this.component = $('<button>', {
        class: 'button glass',
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

extend(Button, Component);

ComponentFactory.register('button', function(params) {
    return new Button(params);
});

//======================================================================

/**
 * Vertical layout section
 */
function Vertical(params) {	
    this.compIds = [];

    this.component = $('<df-vertical>');

    for(var i=0; i<params.components.length; ++i) {
        var element = params.components[i];
        var comp = ComponentFactory.create(element);
        var cell = $('<df-cell>');
        cell.append(comp.dom());
        this.component.append(cell);
        this.compIds[element.id] = true;
    }
}

extend(Vertical, Component);

Vertical.prototype.update = function(params) {
    for(var i=0; i<params.components.length; ++i) {
        var element = params.components[i];

        if(this.compIds[element.id]){
            // Element exists
        } else {
            // Element is new
            var comp = ComponentFactory.create(element);
            var cell = $('<df-cell>');
            cell.append(comp.dom());
            this.component.append(cell);
            this.compIds[element.id] = true;
        }
    }
}

ComponentFactory.register('vertical', function(params) {
    return new Vertical(params);
});

//=============================================================

/**
 * Horizontal layout section
 */
function Horizontal(params) {	
    this.compIds = [];

    this.component = $('<df-horizontal>');

    for(var i=0; i<params.components.length; ++i) {
        var element = params.components[i];
        var comp = ComponentFactory.create(element);
        var cell = $('<df-cell>');
        cell.append(comp.dom());
        this.component.append(cell);
        this.compIds[element.id] = true;
    }
}

extend(Horizontal, Vertical);

ComponentFactory.register('horizontal', function(params) {
    return new Horizontal(params);
});

//=============================================================

/**
 * InputText component section
 */
function InputText(params) {
    this.component = $('<input>', {
        'type' : 'text',
        'value' : params.value,
        //'placeholder' : params.placeholder,
        'class' : 'glass'
    });

    this.component.change(function() {
        DarkForce.fireEvent({
            type: 'change',
            id: params.id,
            value: $(this).val()
        });
    });
}

extend(InputText, Component);

ComponentFactory.register('inputText', function(params) {
    return new InputText(params);
});