(this.webpackJsonpadmin=this.webpackJsonpadmin||[]).push([[0],{383:function(e,t,a){},384:function(e,t,a){},519:function(e,t,a){"use strict";a.r(t);var c=a(0),s=a.n(c),r=a(17),o=a.n(r),j=(a(383),a(384),a(604)),l=a(82),n=a(603),b=a(347),i=a(8),u=a(610),d=a(621),O=a(590),x=a(3),p=function(e){return Object(x.jsx)(u.a,Object(i.a)(Object(i.a)({title:"Nueva Categor\xeda"},e),{},{children:Object(x.jsx)(d.a,{redirect:"list",children:Object(x.jsx)(O.a,{source:"name",label:"Nombre",autoFocus:!0})})}))},m=a(606),h=a(605),f=a(597),g=a(598),v=a(612),P=function(e){return Object(x.jsx)(m.a,Object(i.a)(Object(i.a)({title:"Categor\xedas"},e),{},{perPage:25,pagination:!1,children:Object(x.jsxs)(h.a,{children:[Object(x.jsx)(f.a,{source:"name",label:"Nombre"}),Object(x.jsx)(g.a,{basePath:"categories",undoable:!1}),Object(x.jsx)(v.a,{basePath:"categories",undoable:!1})]})}))},I=a(608),N=function(e){return Object(x.jsx)(I.a,Object(i.a)(Object(i.a)({title:"Editar Categor\xeda"},e),{},{children:Object(x.jsx)(d.a,{children:Object(x.jsx)(O.a,{source:"name",label:"Nombre",autoFocus:!0})})}))},C=a(314),y=a(613),E=a(599),T=[Object(C.h)(),Object(C.d)(2)],M=(Object(C.c)((function(e){return(0,e.translate)("myroot.validation.email_invalid")})),Object(C.f)(),Object(C.e)(18),Object(C.g)(/^\d{5}$/,"Must be a valid Zip Code"),Object(C.a)(["m","f"],"Must be Male or Female"),function(e){return Object(x.jsx)(u.a,Object(i.a)(Object(i.a)({title:"Nuevo Producto"},e),{},{children:Object(x.jsxs)(d.a,{redirect:"list",children:[Object(x.jsx)(y.a,{label:"Categor\xeda",source:"catId",reference:"api/v1/categories",children:Object(x.jsx)(E.a,{optionText:"name",validate:T})}),Object(x.jsx)(O.a,{source:"name",label:"Nombre",validate:Object(C.h)(),autoFocus:!0}),Object(x.jsx)(O.a,{source:"manufacturingCost",validate:Object(C.h)(),label:"Costo"}),Object(x.jsx)(O.a,{source:"material",label:"Material"}),Object(x.jsx)(y.a,{label:"Color/Estampa",source:"colorId",reference:"api/v1/colors",children:Object(x.jsx)(E.a,{optionText:"name"})}),Object(x.jsx)(O.a,{source:"idMl",label:"idMl"}),Object(x.jsx)(O.a,{source:"photoId",label:"Foto"}),Object(x.jsx)(O.a,{source:"notes",label:"Notas"})]})}))}),_=a(615),D=(a(619),a(600),a(620),a(601)),F=a(622),B=a(602),S=function(e){return Object(x.jsxs)(_.a,Object(i.a)(Object(i.a)({},e),{},{children:[Object(x.jsx)(O.a,{label:"Search",source:"q",alwaysOn:!0,allowEmpty:!0}),Object(x.jsx)(y.a,{label:"Categor\xeda",source:"catId",reference:"api/v1/categories",alwaysOn:!0,allowEmpty:!0,children:Object(x.jsx)(E.a,{optionText:"name"})})]}))},V=function(e){return Object(x.jsx)(m.a,Object(i.a)(Object(i.a)({title:"Productos",filters:Object(x.jsx)(S,{})},e),{},{perPage:25,sort:{field:"id",order:"DESC"},children:Object(x.jsxs)(h.a,{children:[Object(x.jsx)(f.a,{source:"id",label:"SKU"}),Object(x.jsx)(D.a,{source:"photoId",label:"Foto",sortable:!1,render:function(e){return null!==e.photoId?Object(x.jsx)("img",{src:"https://lh3.google.com/u/0/d/".concat(e.photoId,"=w80-h80"),alt:e.name}):Object(x.jsx)("p",{children:"Sin Foto"})}}),Object(x.jsx)(F.a,{label:"Categor\xeda",source:"catId",reference:"api/v1/categories",sortBy:"cat_id",children:Object(x.jsx)(f.a,{source:"name"})}),Object(x.jsx)(f.a,{source:"name",label:"Nombre"}),Object(x.jsx)(f.a,{source:"material",label:"Material"}),Object(x.jsx)(F.a,{label:"Color/Estampa",source:"colorId",reference:"api/v1/colors",sortBy:"color_id",children:Object(x.jsx)(f.a,{source:"name"})}),Object(x.jsx)(f.a,{source:"manufacturingCost",label:"Costo"}),Object(x.jsx)(f.a,{source:"precioMayor",sortBy:"manufacturing_cost",label:"Mayor"}),Object(x.jsx)(f.a,{source:"precioCapilla",sortBy:"manufacturing_cost",label:"Capilla"}),Object(x.jsx)(f.a,{source:"precioMenor",sortBy:"manufacturing_cost",label:"Menor"}),Object(x.jsx)(f.a,{source:"precioMl",sortBy:"manufacturing_cost",label:"ML"}),Object(x.jsx)(D.a,{source:"idMl",label:"ML",sortable:!1,render:function(e){return null!==e.mlId?Object(x.jsx)("a",{href:"https://articulo.mercadolibre.com.ar/MLA-".concat(e.idMl),target:"_blank",children:e.idMl}):Object(x.jsx)("p",{children:"No Publicado"})}}),Object(x.jsx)(B.a,{source:"dateCreated",sortBy:"date_created"}),Object(x.jsx)(g.a,{basePath:"products",undoable:!0}),Object(x.jsx)(v.a,{basePath:"products",undoable:!0})]})}))},R=[Object(C.h)(),Object(C.d)(2)],L=function(e){return Object(x.jsx)(I.a,Object(i.a)(Object(i.a)({title:"Editar Producto"},e),{},{children:Object(x.jsxs)(d.a,{redirect:"list",children:[Object(x.jsx)(y.a,{label:"Categor\xeda",source:"catId",reference:"api/v1/categories",children:Object(x.jsx)(E.a,{optionText:"name",validate:R})}),Object(x.jsx)(O.a,{source:"name",label:"Nombre",validate:Object(C.h)(),autoFocus:!0}),Object(x.jsx)(O.a,{source:"manufacturingCost",validate:Object(C.h)(),label:"Costo"}),Object(x.jsx)(O.a,{source:"material",label:"Material"}),Object(x.jsx)(y.a,{label:"Color/Estampa",source:"colorId",reference:"api/v1/colors",children:Object(x.jsx)(E.a,{optionText:"name"})}),Object(x.jsx)(O.a,{source:"idMl",label:"idMl"}),Object(x.jsx)(O.a,{source:"photoId",label:"Foto"}),Object(x.jsx)(O.a,{source:"notes",label:"Notas"})]})}))},A=a(609),w=function(e){return Object(x.jsx)(u.a,Object(i.a)(Object(i.a)({title:"Nuevo Usuario"},e),{},{children:Object(x.jsxs)(d.a,{redirect:"list",children:[Object(x.jsx)(O.a,{source:"firstName",label:"Nombre",autoFocus:!0}),Object(x.jsx)(A.a,{source:"role",choices:[{id:"PROVEEDOR",name:"PROVEEDOR"},{id:"CLIENTE",name:"CLIENTE"},{id:"ADMIN",name:"ADMIN"},{id:"VENDEDOR",name:"VENDEDOR"}]})]})}))},U=function(e){return Object(x.jsx)(m.a,Object(i.a)(Object(i.a)({title:"Usuarios"},e),{},{perPage:25,pagination:!1,children:Object(x.jsxs)(h.a,{children:[Object(x.jsx)(f.a,{source:"firstName",label:"Nombre"}),Object(x.jsx)(f.a,{source:"role",label:"Rol"}),Object(x.jsx)(g.a,{basePath:"users",undoable:!0}),Object(x.jsx)(v.a,{basePath:"users",undoable:!0})]})}))},k=function(e){return Object(x.jsx)(I.a,Object(i.a)(Object(i.a)({title:"Editar Producto"},e),{},{children:Object(x.jsxs)(d.a,{redirect:"list",children:[Object(x.jsx)(O.a,{source:"firstName",label:"Nombre",autoFocus:!0}),Object(x.jsx)(A.a,{source:"role",choices:[{id:"PROVEEDOR",name:"PROVEEDOR"},{id:"CLIENTE",name:"CLIENTE"},{id:"ADMIN",name:"ADMIN"},{id:"VENDEDOR",name:"VENDEDOR"}]})]})}))},q=function(e){return Object(x.jsx)(m.a,Object(i.a)(Object(i.a)({title:"Colores"},e),{},{perPage:25,pagination:!1,children:Object(x.jsxs)(h.a,{children:[Object(x.jsx)(f.a,{source:"name",label:"Nombre"}),Object(x.jsx)(g.a,{basePath:"colors",undoable:!1}),Object(x.jsx)(v.a,{basePath:"colors",undoable:!1})]})}))},K=function(e){return Object(x.jsx)(u.a,Object(i.a)(Object(i.a)({title:"Nuevo Color"},e),{},{children:Object(x.jsx)(d.a,{redirect:"list",children:Object(x.jsx)(O.a,{source:"name",label:"Nombre",autoFocus:!0})})}))},$=function(e){return Object(x.jsx)(I.a,Object(i.a)(Object(i.a)({title:"Editar Color"},e),{},{children:Object(x.jsx)(d.a,{children:Object(x.jsx)(O.a,{source:"name",label:"Nombre",autoFocus:!0})})}))},z=function(e){return Object(x.jsx)(u.a,Object(i.a)(Object(i.a)({title:"Nuevo Precio"},e),{},{children:Object(x.jsxs)(d.a,{redirect:"list",children:[Object(x.jsx)(O.a,{source:"cloth_1_name",label:"Nombre de la Tela 1",autoFocus:!0}),Object(x.jsx)(O.a,{source:"cloth_1_amount",label:"Cantidad de Tela 1 (m2)"}),Object(x.jsx)(O.a,{source:"cloth_2_name",label:"Nombre de la Tela 2"}),Object(x.jsx)(O.a,{source:"cloth_2_amount",label:"Cantidad de Tela 2 (m2)"}),Object(x.jsx)(O.a,{source:"elastEmb",label:"elastEmb (m)"}),Object(x.jsx)(O.a,{source:"elastCintura",label:"elastCintura (m)"}),Object(x.jsx)(O.a,{source:"elastBajoB",label:"elastBajoB (m)"}),Object(x.jsx)(O.a,{source:"puntilla",label:"puntilla (m)"}),Object(x.jsx)(O.a,{source:"bretel",label:"bretel (m)"}),Object(x.jsx)(O.a,{source:"argollas",label:"argollas (unidades)"}),Object(x.jsx)(O.a,{source:"ganchos",label:"ganchos (unidades)"}),Object(x.jsx)(O.a,{source:"reguladores",label:"reguladores (unidades)"}),Object(x.jsx)(O.a,{source:"manoDeObra",label:"Costo de mano de obra ($)"})]})}))},J=function(e){return Object(x.jsx)(I.a,Object(i.a)(Object(i.a)({title:"Editar Color"},e),{},{children:Object(x.jsxs)(d.a,{children:[Object(x.jsx)(O.a,{source:"cloth_1_name",label:"Nombre de la Tela 1",autoFocus:!0}),Object(x.jsx)(O.a,{source:"cloth_1_amount",label:"Cantidad de Tela 1 (m2)"}),Object(x.jsx)(O.a,{source:"cloth_2_name",label:"Nombre de la Tela 2"}),Object(x.jsx)(O.a,{source:"cloth_2_amount",label:"Cantidad de Tela 2 (m2)"}),Object(x.jsx)(O.a,{source:"elastEmb",label:"elastEmb (m)"}),Object(x.jsx)(O.a,{source:"elastCintura",label:"elastCintura (m)"}),Object(x.jsx)(O.a,{source:"elastBajoB",label:"elastBajoB (m)"}),Object(x.jsx)(O.a,{source:"puntilla",label:"puntilla (m)"}),Object(x.jsx)(O.a,{source:"bretel",label:"bretel (m)"}),Object(x.jsx)(O.a,{source:"argollas",label:"argollas (unidades)"}),Object(x.jsx)(O.a,{source:"ganchos",label:"ganchos (unidades)"}),Object(x.jsx)(O.a,{source:"reguladores",label:"reguladores (unidades)"}),Object(x.jsx)(O.a,{source:"manoDeObra",label:"Costo de mano de obra ($)"})]})}))},G=function(e){return Object(x.jsx)(m.a,Object(i.a)(Object(i.a)({title:"Precios"},e),{},{perPage:25,pagination:!1,children:Object(x.jsxs)(h.a,{children:[Object(x.jsx)(f.a,{source:"cloth_1_name",label:"Nombre de la Tela 1"}),Object(x.jsx)(g.a,{basePath:"prices",undoable:!1}),Object(x.jsx)(v.a,{basePath:"prices",undoable:!1})]})}))},X=[{id:1,name:"name"},{id:2,name:"name"}],Z=function(e){return"".concat(e.id," ").concat(e.name)},H=function(e){return Object(x.jsx)(u.a,Object(i.a)(Object(i.a)({title:"Nuevo Movimiento de Stock"},e),{},{children:Object(x.jsxs)(d.a,{redirect:"list",children:[Object(x.jsx)(y.a,{perPage:!1,label:"Producto",source:"productId",reference:"api/v1/products",children:Object(x.jsx)(E.a,{choices:X,optionText:Z})}),Object(x.jsx)(y.a,{label:"Usuario",source:"userId",reference:"api/v1/users",sortBy:"firstName",children:Object(x.jsx)(E.a,{optionText:"firstName"})}),Object(x.jsx)(O.a,{source:"t1"}),Object(x.jsx)(O.a,{source:"t2"}),Object(x.jsx)(O.a,{source:"t3"}),Object(x.jsx)(O.a,{source:"t4"}),Object(x.jsx)(O.a,{source:"t5"}),Object(x.jsx)(O.a,{source:"t6"}),Object(x.jsx)(O.a,{source:"t7"}),Object(x.jsx)(O.a,{source:"t8"}),Object(x.jsx)(O.a,{source:"t9"}),Object(x.jsx)(O.a,{source:"t10"}),Object(x.jsx)(O.a,{source:"t11"})]})}))},Q=[{id:1,name:"name"},{id:2,name:"name"}],W=function(e){return"".concat(e.id," ").concat(e.name)},Y=function(e){return Object(x.jsx)(I.a,Object(i.a)(Object(i.a)({title:"Editar Movimiento de Stock"},e),{},{children:Object(x.jsxs)(d.a,{redirect:"list",children:[Object(x.jsx)(y.a,{perPage:!1,label:"Producto",source:"productId",reference:"api/v1/products",children:Object(x.jsx)(E.a,{choices:Q,optionText:W})}),Object(x.jsx)(y.a,{label:"Usuario",source:"userId",reference:"api/v1/users",sortBy:"firstName",children:Object(x.jsx)(E.a,{optionText:"firstName"})}),Object(x.jsx)(O.a,{source:"t1"}),Object(x.jsx)(O.a,{source:"t2"}),Object(x.jsx)(O.a,{source:"t3"}),Object(x.jsx)(O.a,{source:"t4"}),Object(x.jsx)(O.a,{source:"t5"}),Object(x.jsx)(O.a,{source:"t6"}),Object(x.jsx)(O.a,{source:"t7"}),Object(x.jsx)(O.a,{source:"t8"}),Object(x.jsx)(O.a,{source:"t9"}),Object(x.jsx)(O.a,{source:"t10"}),Object(x.jsx)(O.a,{source:"t11"})]})}))},ee=function(e){return Object(x.jsxs)(_.a,Object(i.a)(Object(i.a)({},e),{},{children:[Object(x.jsx)(y.a,{perPage:!1,label:"Producto",source:"productId",reference:"api/v1/products",allowEmpty:!0,alwaysOn:!0,children:Object(x.jsx)(E.a,{choises:[{id:1,name:"name"}],optionText:function(e){return"".concat(e.id," ").concat(e.name)}})}),Object(x.jsx)(y.a,{perPage:!1,label:"Usuario",source:"userId",reference:"api/v1/users",allowEmpty:!0,alwaysOn:!0,children:Object(x.jsx)(E.a,{optionText:"firstName"})})]}))},te=function(e){return Object(x.jsx)(m.a,Object(i.a)(Object(i.a)({title:"Stock",filters:Object(x.jsx)(ee,{})},e),{},{perPage:25,sort:{field:"id",order:"DESC"},children:Object(x.jsxs)(h.a,{children:[Object(x.jsx)(F.a,{label:"Foto",source:"productId",reference:"api/v1/products",sortBy:"product_id",children:Object(x.jsx)(D.a,{source:"photoId",label:"Foto",sortable:!1,render:function(e){return null!==e.photoId?Object(x.jsx)("img",{src:"https://lh3.google.com/u/0/d/".concat(e.photoId,"=w80-h80"),alt:e.name}):Object(x.jsx)("p",{children:"Sin Foto"})}})}),Object(x.jsx)(F.a,{label:"Producto",source:"productId",reference:"api/v1/products",sortBy:"product_id",children:Object(x.jsx)(D.a,{label:"Nombre",render:function(e){return"SKU ".concat(e.id," - ").concat(e.name)}})}),Object(x.jsx)(F.a,{label:"Usuario",source:"userId",reference:"api/v1/users",sortBy:"user_id",children:Object(x.jsx)(f.a,{source:"firstName"})}),Object(x.jsx)(f.a,{sortable:!1,source:"t1"}),Object(x.jsx)(f.a,{sortable:!1,source:"t2"}),Object(x.jsx)(f.a,{sortable:!1,source:"t3"}),Object(x.jsx)(f.a,{sortable:!1,source:"t4"}),Object(x.jsx)(f.a,{sortable:!1,source:"t5"}),Object(x.jsx)(f.a,{sortable:!1,source:"t6"}),Object(x.jsx)(f.a,{sortable:!1,source:"t7"}),Object(x.jsx)(f.a,{sortable:!1,source:"t8"}),Object(x.jsx)(f.a,{sortable:!1,source:"t9"}),Object(x.jsx)(f.a,{sortable:!1,source:"t10"}),Object(x.jsx)(f.a,{sortable:!1,source:"t11"}),Object(x.jsx)(f.a,{sortable:!1,source:"notes",label:"Notes"}),Object(x.jsx)(B.a,{source:"dateCreated"}),Object(x.jsx)(g.a,{basePath:"stock",undoable:!0}),Object(x.jsx)(v.a,{basePath:"stock",undoable:!0})]})}))},ae=function(e){return Object(x.jsx)(u.a,Object(i.a)(Object(i.a)({title:"Nueva Venta"},e),{},{children:Object(x.jsxs)(d.a,{initialValues:function(){return{paymentMethodId:6,priceId:10}},redirect:"edit",children:[Object(x.jsx)(O.a,{source:"notes",validate:Object(C.h)()}),Object(x.jsx)(y.a,{label:"De Lugar de Venta / Vendedor",source:"sellerId",reference:"api/v1/users",children:Object(x.jsx)(E.a,{optionText:"firstName"})}),Object(x.jsx)(y.a,{label:"A Cliente",source:"clientId",reference:"api/v1/users",children:Object(x.jsx)(E.a,{optionText:"firstName"})}),Object(x.jsx)(y.a,{label:"M\xe9todo de Pago",source:"paymentMethodId",reference:"api/v1/options/type/paymentMethod",children:Object(x.jsx)(E.a,{optionText:"name"})}),Object(x.jsx)(y.a,{label:"Precio",source:"priceId",reference:"api/v1/options/type/price",children:Object(x.jsx)(E.a,{optionText:"name"})})]})}))},ce=a(607),se=a(614),re=a(623),oe=a(22),je=a(332),le=a.n(je),ne=a(30),be=function(e){var t=e.record;return Object(x.jsx)(ne.a,{variant:"raised",component:oe.a,to:"/api/v1/saleproducts/create?saleId=".concat(t.id),label:"Agregar Productos",title:"Agregar Productos",children:Object(x.jsx)(le.a,{})})},ie=function(e){return Object(x.jsx)(I.a,Object(i.a)(Object(i.a)({title:"Editar Venta"},e),{},{children:Object(x.jsxs)(ce.a,{redirect:"edit",children:[Object(x.jsxs)(se.a,{label:"Summary",children:[Object(x.jsx)(O.a,{source:"notes",validate:Object(C.h)()}),Object(x.jsx)(y.a,{label:"De Lugar de Venta / Vendedor",source:"sellerId",reference:"api/v1/users",children:Object(x.jsx)(E.a,{optionText:"firstName"})}),Object(x.jsx)(y.a,{label:"A Cliente",source:"clientId",reference:"api/v1/users",children:Object(x.jsx)(E.a,{optionText:"firstName"})}),Object(x.jsx)(y.a,{label:"M\xe9todo de Pago",source:"paymentMethodId",reference:"api/v1/options/type/paymentMethod",children:Object(x.jsx)(E.a,{optionText:"name"})}),Object(x.jsx)(y.a,{label:"Precio",source:"priceId",reference:"api/v1/options/type/price",children:Object(x.jsx)(E.a,{optionText:"name"})})]}),Object(x.jsxs)(se.a,{label:"Productos",path:"products",children:[Object(x.jsx)(re.a,{addLabel:!1,reference:"api/v1/saleproducts",target:"saleId",sort:{field:"id",order:"DESC"},children:Object(x.jsxs)(h.a,{children:[Object(x.jsx)(F.a,{label:"Producto",source:"productId",reference:"api/v1/products",sortBy:"name",children:Object(x.jsx)(D.a,{label:"Nombre",render:function(e){return"SKU ".concat(e.id," - ").concat(e.name)}})}),Object(x.jsx)(f.a,{source:"size",label:"Talle"}),Object(x.jsx)(f.a,{source:"quantity",label:"Cantidad"}),Object(x.jsx)(f.a,{source:"manufacturingCostTotal",label:"$"}),Object(x.jsx)(f.a,{source:"notes",label:"Notas"}),Object(x.jsx)(g.a,{})]})}),Object(x.jsx)("br",{}),Object(x.jsx)(be,Object(i.a)({},e))]})]})}))},ue=a(333),de=a(334),Oe=a(288),xe=function(){var e=Object(Oe.a)({type:"getList",resource:"api/v1/sales",payload:{pagination:{page:1,perPage:10},sort:{field:"id",order:"DESC"}}}),t=e.data,a=(e.total,e.loading),c=e.error;if(a)return Object(x.jsx)(ue.a,{});if(c)return Object(x.jsx)(de.a,{});if(console.log(t),!t||0===t.length)return null;var s=t.map((function(e){return e.total})).reduce((function(e,t){return e+t}));return console.log(s),Object(x.jsx)("ul",{children:Object(x.jsxs)("li",{children:["Total: ",s]})})},pe=function(e){return Object(x.jsxs)("div",{children:[Object(x.jsx)(m.a,Object(i.a)(Object(i.a)({title:"Sales"},e),{},{perPage:25,sort:{field:"id",order:"DESC"},children:Object(x.jsxs)(h.a,{children:[Object(x.jsx)(f.a,{source:"notes",label:"Notas"}),Object(x.jsx)(F.a,{label:"De Lugar de Venta / Vendedor",source:"sellerId",reference:"api/v1/users",sortBy:"firstName",children:Object(x.jsx)(f.a,{source:"firstName"})}),Object(x.jsx)(F.a,{label:"A Cliente",source:"clientId",reference:"api/v1/users",sortBy:"firstName",children:Object(x.jsx)(f.a,{source:"firstName"})}),Object(x.jsx)(F.a,{label:"M\xe9todo de Pago",source:"paymentMethodId",reference:"api/v1/options/type/paymentMethod",children:Object(x.jsx)(f.a,{source:"name"})}),Object(x.jsx)(F.a,{label:"Precio",source:"priceId",reference:"api/v1/options/type/price",children:Object(x.jsx)(f.a,{source:"name"})}),Object(x.jsx)(f.a,{source:"total"}),Object(x.jsx)(B.a,{source:"dateCreated",label:"Fecha"}),Object(x.jsx)(g.a,{basePath:"sales",undoable:!0,label:"Editar / Agregar Productos"}),Object(x.jsx)(v.a,{basePath:"sales",undoable:!0,label:"Eliminar"})]})})),Object(x.jsx)(xe,{})]})},me=a(53),he=[{id:1,name:"name"}],fe=function(e){return"".concat(e.id," ").concat(e.name)},ge=function(e){var t=Object(me.parse)(e.location.search).saleId,a=t?parseInt(t,10):"",c=a?"/api/v1/sales/".concat(a,"/products"):"list";return Object(x.jsx)(u.a,Object(i.a)(Object(i.a)({title:"Nuevo SaleProduct"},e),{},{children:Object(x.jsxs)(d.a,{initialValues:function(){return{saleId:a}},redirect:c,children:[Object(x.jsx)(y.a,{validate:Object(C.h)(),perPage:!1,label:"Venta",source:"saleId",reference:"api/v1/sales",children:Object(x.jsx)(E.a,{optionText:"notes"})}),Object(x.jsx)(y.a,{validate:Object(C.h)(),perPage:!1,label:"Producto",source:"productId",reference:"api/v1/products",children:Object(x.jsx)(A.a,{choices:he,optionText:fe})}),Object(x.jsx)(O.a,{validate:Object(C.h)(),source:"size",label:"Talle"}),Object(x.jsx)(O.a,{validate:Object(C.h)(),source:"quantity",label:"Cantidad"}),Object(x.jsx)(O.a,{source:"customPrice",label:"Otro Precio"}),Object(x.jsx)(O.a,{source:"notes",label:"Notas"})]})}))},ve=[{id:1,name:"name"},{id:2,name:"name"}],Pe=function(e){return"".concat(e.id," ").concat(e.name)},Ie=function(e){var t=Object(me.parse)(e.location.search).saleId,a=t?parseInt(t,10):"",c=a?"/api/v1/sales/".concat(a,"/products"):"list";return Object(x.jsx)(I.a,Object(i.a)(Object(i.a)({title:"Nuevo SaleProduct"},e),{},{children:Object(x.jsxs)(d.a,{initialValues:{saleId:a},redirect:c,children:[Object(x.jsx)(y.a,{perPage:!1,label:"Venta",source:"saleId",reference:"api/v1/sales",children:Object(x.jsx)(E.a,{optionText:"notes"})}),Object(x.jsx)(y.a,{perPage:!1,label:"Producto",source:"productId",reference:"api/v1/products",children:Object(x.jsx)(E.a,{choices:ve,optionText:Pe})}),Object(x.jsx)(O.a,{source:"size",label:"Talle"}),Object(x.jsx)(O.a,{source:"quantity",label:"Cantidad"}),Object(x.jsx)(O.a,{source:"customPrice",label:"Otro Precio"}),Object(x.jsx)(O.a,{source:"notes",label:"Notas"})]})}))},Ne=function(e){return Object(x.jsx)(m.a,Object(i.a)(Object(i.a)({title:"SalesProducts"},e),{},{perPage:25,children:Object(x.jsxs)(h.a,{children:[Object(x.jsx)(F.a,{label:"Foto",source:"productId",reference:"api/v1/products",sortBy:"product_id",children:Object(x.jsx)(D.a,{source:"photoId",label:"Foto",sortable:!1,render:function(e){return null!==e.photoId?Object(x.jsx)("img",{src:"https://lh3.google.com/u/0/d/".concat(e.photoId,"=w80-h80"),alt:e.name}):Object(x.jsx)("p",{children:"Sin Foto"})}})}),Object(x.jsx)(F.a,{label:"Producto",source:"productId",reference:"api/v1/products",children:Object(x.jsx)(D.a,{label:"Nombre",render:function(e){return"SKU ".concat(e.id," - ").concat(e.name)}})}),Object(x.jsx)(F.a,{label:"Venta",source:"saleId",reference:"api/v1/sales",children:Object(x.jsx)(f.a,{source:"notes"})}),Object(x.jsx)(f.a,{source:"size",label:"Talle"}),Object(x.jsx)(f.a,{source:"quantity",label:"Cantidad"}),Object(x.jsx)(f.a,{source:"manufacturingCostTotal",label:"$"}),Object(x.jsx)(f.a,{source:"notes",label:"Notas"}),Object(x.jsx)(g.a,{basePath:"saleproducts",undoable:!0}),Object(x.jsx)(v.a,{basePath:"saleproducts",undoable:!0})]})}))},Ce=function(e){return Object(x.jsx)(u.a,Object(i.a)(Object(i.a)({title:"Nueva Opci\xf3n"},e),{},{children:Object(x.jsxs)(d.a,{redirect:"list",children:[Object(x.jsx)(O.a,{source:"name",label:"Nombre",autoFocus:!0}),Object(x.jsx)(A.a,{source:"type",label:"Tipo",choices:[{id:"rol",name:"ROL"},{id:"paymentMethod",name:"METODO DE PAGO"},{id:"price",name:"PRECIO"}]})]})}))},ye=function(e){return Object(x.jsx)(I.a,Object(i.a)(Object(i.a)({title:"Editar Opci\xf3n"},e),{},{children:Object(x.jsxs)(d.a,{children:[Object(x.jsx)(O.a,{source:"name",label:"Nombre",autoFocus:!0}),Object(x.jsx)(A.a,{source:"type",label:"Tipo",choices:[{id:"rol",name:"ROL"},{id:"paymentMethod",name:"METODO DE PAGO"},{id:"price",name:"PRECIO"}]})]})}))},Ee=function(e){return Object(x.jsx)(m.a,Object(i.a)(Object(i.a)({title:"Opciones"},e),{},{perPage:25,pagination:!1,children:Object(x.jsxs)(h.a,{children:[Object(x.jsx)(f.a,{source:"name",label:"Nombre"}),Object(x.jsx)(f.a,{source:"type",label:"Tipo"}),Object(x.jsx)(g.a,{basePath:"options",undoable:!1}),Object(x.jsx)(v.a,{basePath:"options",undoable:!1})]})}))},Te=function(e){return Object(x.jsx)(m.a,Object(i.a)(Object(i.a)({title:"Disponibilidad de Productos"},e),{},{perPage:25,children:Object(x.jsxs)(h.a,{children:[Object(x.jsx)(F.a,{label:"Producto",source:"productId",reference:"api/v1/products",sortBy:"name",children:Object(x.jsx)(D.a,{label:"Nombre",render:function(e){return"SKU ".concat(e.id," - ").concat(e.name)}})}),Object(x.jsx)(f.a,{source:"t1"}),Object(x.jsx)(f.a,{source:"t2"}),Object(x.jsx)(f.a,{source:"t3"}),Object(x.jsx)(f.a,{source:"t4"}),Object(x.jsx)(f.a,{source:"t5"}),Object(x.jsx)(f.a,{source:"t6"}),Object(x.jsx)(f.a,{source:"t7"}),Object(x.jsx)(f.a,{source:"t8"}),Object(x.jsx)(f.a,{source:"t9"}),Object(x.jsx)(f.a,{source:"t10"}),Object(x.jsx)(f.a,{source:"t11"})]})}))};var Me=function(){var e="https://sleepy-beach-97825.herokuapp.com";return void 0===e&&(e="REACT_APP_API_URL undefined"),console.log(e),Object(x.jsxs)(j.a,{dataProvider:Object(b.a)(e,l.a.fetchJson,"X-Total-Count"),children:[Object(x.jsx)(n.a,{name:"api/v1/products",options:{label:"Productos"},list:V,create:M,edit:L}),Object(x.jsx)(n.a,{name:"api/v1/stock",options:{label:"Stock"},list:te,create:H,edit:Y}),Object(x.jsx)(n.a,{name:"api/v1/sales",options:{label:"Ventas"},list:pe,create:ae,edit:ie}),Object(x.jsx)(n.a,{name:"api/v1/users",options:{label:"Usuarios"},list:U,create:w,edit:k}),Object(x.jsx)(n.a,{name:"api/v1/categories",options:{label:"Categor\xedas"},list:P,create:p,edit:N}),Object(x.jsx)(n.a,{name:"api/v1/colors",options:{label:"Colores"},list:q,create:K,edit:$}),Object(x.jsx)(n.a,{name:"api/v1/prices",options:{label:"Precios"},list:G,create:z,edit:J}),Object(x.jsx)(n.a,{name:"api/v1/saleproducts",options:{label:"SaleProducts"},list:Ne,create:ge,edit:Ie}),Object(x.jsx)(n.a,{name:"api/v1/options",options:{label:"Opciones"},list:Ee,create:Ce,edit:ye}),Object(x.jsx)(n.a,{name:"api/v1/report",options:{label:"Reporte"},list:Te}),Object(x.jsx)(n.a,{name:"api/v1/options/type/paymentMethod"}),Object(x.jsx)(n.a,{name:"api/v1/options/type/price"}),Object(x.jsx)(n.a,{name:"api/v1/options/type/rol"})]})},_e=function(e){e&&e instanceof Function&&a.e(3).then(a.bind(null,625)).then((function(t){var a=t.getCLS,c=t.getFID,s=t.getFCP,r=t.getLCP,o=t.getTTFB;a(e),c(e),s(e),r(e),o(e)}))};o.a.render(Object(x.jsx)(s.a.StrictMode,{children:Object(x.jsx)(Me,{})}),document.getElementById("root")),_e()}},[[519,1,2]]]);
//# sourceMappingURL=main.6aa0b4f1.chunk.js.map