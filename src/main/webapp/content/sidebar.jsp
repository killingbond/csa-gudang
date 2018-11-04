<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.zkoss.org/jsp/zul" prefix="z"%>
<z:page>
	<z:navbar orient="vertical" sclass="sidebar" apply="org.zkoss.bind.BindComposer"
			viewModel="@id('vm')@init('com.ditzweb.controllers.SidebarController')" >
		
		<security:authorize
			access="hasAnyRole('ROLE_ADMIN','ROLE_OWNER','ROLE_MARKETING')">
			<z:navitem iconSclass="icon icon-white icon-home" label="Dashboard" href="index.zul" selected="@load(vm.dashboard)"/>
			<z:navitem iconSclass="icon icon-white icon-th" label="User"
				href="user.zul" selected="@load(vm.user)" />
			<z:navitem iconSclass="icon icon-white icon-th" label="User Role" href="roles.zul" selected="@load(vm.roles)"  />
			<z:navitem iconSclass="icon icon-white icon-th" label="Supplier" href="supplier.zul" selected="@load(vm.supplier)" />
			<z:navitem iconSclass="icon icon-white icon-th" label="Client" href="client.zul" selected="@load(vm.client)" />
			<z:navitem iconSclass="icon icon-white icon-th" label="Quantity" href="quantity.zul" selected="@load(vm.quantity)"/>
			<z:navitem iconSclass="icon icon-white icon-th" label="Items" href="items.zul"  selected="@load(vm.items)" />
			<z:navitem iconSclass="icon icon-white icon-th"
				label="Items Quantity" href="itemsquantity.zul"  selected="@load(vm.itemsquantity)"/>
			<z:navitem iconSclass="icon icon-white icon-th"
				label="Quantity Conversion" href="quantityconversion.zul"  selected="@load(vm.quantityconversion)"/>
		</security:authorize>

		<security:authorize
			access="hasAnyRole('ROLE_STOKISH','ROLE_OWNER','ROLE_MARKETING')">
			<z:navitem iconSclass="icon icon-white icon-th" label="Stock"
				href="stock.zul"  selected="@load(vm.stock)" />
			<z:navitem iconSclass="icon icon-white icon-th" label="Transaksi In" href="transaksiin.zul"  selected="@load(vm.transaksiin)" />
			<z:navitem iconSclass="icon icon-white icon-th" label="Transaksi Out" href="transaksiout.zul"  selected="@load(vm.transaksiout)" />
		</security:authorize>
	</z:navbar>
</z:page>