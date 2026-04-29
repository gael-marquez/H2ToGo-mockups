package com.htogo.app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Approval
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.htogo.app.ui.theme.HToGoColors
import com.htogo.app.ui.theme.HToGoTheme
import kotlinx.coroutines.launch

private const val ES_DUENO = true

private data class NegocioInfo(
    val titular: String,
    val nombreComercial: String,
    val iniciales: String,
    val direccion: String,
    val telefono: String,
    val correo: String,
    val totalEntregas: Int,
    val antiguedadMeses: Int
)

private data class VehiculoRepartidor(
    val id: String,
    val tipo: String,
    val marca: String,
    val modelo: String,
    val anio: Int,
    val placas: String,
    val capacidad: Int,
    val esPrincipal: Boolean
)

private enum class EstadoSolicitud { PENDIENTE, APROBADA, RECHAZADA }

private data class SolicitudCambio(
    val id: String,
    val tipo: String,
    val estado: EstadoSolicitud,
    val fecha: String,
    val comentario: String?
)

private data class ProductoPrecio(
    val id: String,
    val codigo: String,
    val marca: String,
    val capacidad: String,
    val precio: Double,
    val accent: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilRepartidorScreen(
    onBack: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val negocio = remember {
        NegocioInfo(
            titular = "Carlos Mendoza Ramírez",
            nombreComercial = "Aguas Del Valle",
            iniciales = "CM",
            direccion = "Av. Cuauhtémoc 145, Col. Roma Norte, CDMX",
            telefono = "+52 55 1234 5678",
            correo = "carlos.mendoza@aguasdelvalle.mx",
            totalEntregas = 1284,
            antiguedadMeses = 8
        )
    }
    val vehiculos = remember {
        mutableStateListOf(
            VehiculoRepartidor("v1", "Pickup", "Nissan", "Frontier", 2020, "RPK-285-A", 30, true),
            VehiculoRepartidor("v2", "Motocicleta", "Italika", "DS150", 2022, "NMR-78-2", 6, false)
        )
    }
    val solicitudes = remember {
        listOf(
            SolicitudCambio("s1", "Datos vehículo", EstadoSolicitud.PENDIENTE, "Hoy · 9:14 am", null),
            SolicitudCambio("s2", "Precio producto · Marca A", EstadoSolicitud.APROBADA, "Ayer", null),
            SolicitudCambio("s3", "Agregar vehículo", EstadoSolicitud.RECHAZADA, "Hace 3 días",
                "Faltan documentos del vehículo. Anexa tarjeta de circulación.")
        )
    }
    val productos = remember {
        listOf(
            ProductoPrecio("a", "CIE", "Marca A", "20 L", 45.0, HToGoColors.Primary),
            ProductoPrecio("b", "BON", "Marca B", "20 L", 48.0, HToGoColors.AccentEmerald),
            ProductoPrecio("c", "EPU", "Marca C", "20 L", 42.0, HToGoColors.AccentAmber)
        )
    }

    var enLinea by remember { mutableStateOf(true) }
    var notifPush by remember { mutableStateOf(true) }
    var notifEmail by remember { mutableStateOf(false) }
    var notifNovedades by remember { mutableStateOf(true) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = HToGoColors.Background,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            item {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .background(Brush.linearGradient(listOf(HToGoColors.PrimaryDark, HToGoColors.Primary)))
                        .padding(start = 8.dp, end = 18.dp, top = 8.dp, bottom = 16.dp)
                        .padding(bottom = 56.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", tint = Color.White)
                        }
                        Text(
                            "Mi perfil",
                            color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.weight(1f).padding(start = 4.dp)
                        )
                        IconButton(onClick = {}) {
                            Icon(Icons.Outlined.NotificationsNone, null, tint = Color.White)
                        }
                        IconButton(onClick = {}) {
                            Icon(Icons.Filled.Settings, null, tint = Color.White)
                        }
                    }
                }
            }

            item {
                ProfileCard(
                    n = negocio,
                    enLinea = enLinea,
                    onToggleLinea = { enLinea = !enLinea },
                    modifier = Modifier
                        .padding(horizontal = 18.dp)
                        .offset(y = (-42).dp)
                )
            }

            item {
                SectionHeader("Información del negocio")
                BadgeNegocio(modifier = Modifier.padding(horizontal = 18.dp))
                Spacer(Modifier.height(8.dp))
                InfoCard {
                    InfoRow(Icons.Filled.Person, "Titular", negocio.titular) {}
                    InfoRow(Icons.Filled.Storefront, "Nombre comercial", negocio.nombreComercial) {}
                    InfoRow(Icons.Filled.Place, "Dirección de la base", negocio.direccion) {}
                    InfoRow(Icons.Filled.Phone, "Teléfono", negocio.telefono) {}
                    InfoRow(Icons.Filled.MailOutline, "Correo electrónico", negocio.correo, last = true) {}
                }
            }

            item {
                Row(
                    Modifier.padding(start = 22.dp, end = 18.dp, top = 18.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "MIS VEHÍCULOS",
                        fontSize = 13.sp, fontWeight = FontWeight.SemiBold,
                        color = HToGoColors.TextSecondary, modifier = Modifier.weight(1f)
                    )
                    TextButton(onClick = {}) { Text("+ Agregar", color = HToGoColors.Primary) }
                }
                Row(
                    Modifier
                        .padding(horizontal = 18.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(HToGoColors.PrimaryWash)
                        .padding(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(Icons.Filled.Info, null, tint = HToGoColors.Primary, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Los vehículos pertenecen al negocio. Cualquier repartidor del negocio puede usarlos.",
                        fontSize = 12.sp, color = HToGoColors.PrimaryDark
                    )
                }
                Spacer(Modifier.height(8.dp))
            }
            items(vehiculos) { v ->
                VehiculoCard(
                    v = v,
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 4.dp)
                )
            }
            item {
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 6.dp)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.5.dp, HToGoColors.Primary)
                ) {
                    Icon(Icons.Filled.AddCircleOutline, null, tint = HToGoColors.Primary)
                    Spacer(Modifier.width(6.dp))
                    Text("Agregar otro vehículo", color = HToGoColors.Primary, fontWeight = FontWeight.SemiBold)
                }
            }

            item {
                SectionHeader("Mis productos y precios")
                Card(
                    modifier = Modifier.padding(horizontal = 18.dp).fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, HToGoColors.OutlineSoft),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column {
                        productos.forEachIndexed { i, p ->
                            ProductoRow(p, isLast = i == productos.lastIndex) {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Solicitud de cambio enviada al admin")
                                }
                            }
                        }
                    }
                }
            }

            if (ES_DUENO) {
                item {
                    SectionHeader("Mis solicitudes de cambio")
                    Card(
                        modifier = Modifier.padding(horizontal = 18.dp).fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, HToGoColors.OutlineSoft),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Column {
                            solicitudes.forEachIndexed { i, s ->
                                SolicitudRow(s, isLast = i == solicitudes.lastIndex)
                            }
                        }
                    }
                }
            }

            item {
                SectionHeader("Horario de atención")
                HorarioCard(modifier = Modifier.padding(horizontal = 18.dp))
            }

            item {
                SectionHeader("Comparte tu purificadora")
                QRCard(modifier = Modifier.padding(horizontal = 18.dp))
                Text(
                    "Los clientes que escaneen este QR podrán hacer pedidos directos a tu purificadora.",
                    fontSize = 11.sp,
                    color = HToGoColors.TextSecondary,
                    modifier = Modifier.padding(horizontal = 22.dp, vertical = 8.dp)
                )
            }

            item {
                SectionHeader("Notificaciones")
                Card(
                    modifier = Modifier.padding(horizontal = 18.dp).fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, HToGoColors.OutlineSoft),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column {
                        SwitchRow(Icons.Filled.NotificationsActive, "Notificaciones push",
                            "Nuevos pedidos y mensajes", notifPush) { notifPush = it }
                        SwitchRow(Icons.Filled.MailOutline, "Notificaciones por correo",
                            "Resúmenes y novedades", notifEmail) { notifEmail = it }
                        SwitchRow(Icons.Filled.Campaign, "Novedades",
                            "Tips para hacer crecer tu negocio", notifNovedades, isLast = true) { notifNovedades = it }
                    }
                }
            }

            item {
                SectionHeader("Cuenta")
                Card(
                    modifier = Modifier.padding(horizontal = 18.dp).fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, HToGoColors.OutlineSoft),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column {
                        ActionRow(Icons.Outlined.Lock, "Cambiar contraseña", redText = false, onClick = {})
                        ActionRow(Icons.Filled.Logout, "Cerrar sesión", redText = true, isLast = true, onClick = onLogout)
                    }
                }
                Spacer(Modifier.height(20.dp))
                Text(
                    "HToGo · v1.0.0",
                    fontSize = 11.sp, color = HToGoColors.TextTertiary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun ProfileCard(
    n: NegocioInfo,
    enLinea: Boolean,
    onToggleLinea: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Brush.linearGradient(listOf(HToGoColors.Primary, HToGoColors.PrimaryLight))),
                    contentAlignment = Alignment.Center
                ) {
                    Text(n.iniciales, color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.width(14.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        n.titular.split(" ").take(2).joinToString(" "),
                        fontSize = 17.sp, fontWeight = FontWeight.SemiBold
                    )
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 2.dp)) {
                        Icon(Icons.Filled.Storefront, null, tint = HToGoColors.Primary, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(
                            n.nombreComercial,
                            fontSize = 13.sp, color = HToGoColors.Primary, fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = HToGoColors.AccentEmerald.copy(alpha = .12f),
                border = BorderStroke(1.dp, HToGoColors.AccentEmerald.copy(alpha = .25f))
            ) {
                Row(Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier
                            .size(8.dp)
                            .clip(RoundedCornerShape(50))
                            .background(HToGoColors.AccentEmerald)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        if (enLinea) "Estás en línea · Recibiendo pedidos" else "Estás desconectado",
                        fontSize = 13.sp, modifier = Modifier.weight(1f)
                    )
                    Switch(checked = enLinea, onCheckedChange = { onToggleLinea() })
                }
            }
            Spacer(Modifier.height(14.dp))
            HorizontalDivider(color = HToGoColors.OutlineSoft)
            Spacer(Modifier.height(12.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                MiniProfileStat("${n.totalEntregas}", "Entregas")
                Box(
                    Modifier
                        .width(1.dp)
                        .height(32.dp)
                        .background(HToGoColors.OutlineSoft)
                )
                MiniProfileStat("${n.antiguedadMeses} mes", "Antigüedad")
            }
        }
    }
}

@Composable
private fun MiniProfileStat(v: String, l: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(v, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(l.uppercase(), fontSize = 10.sp, color = HToGoColors.TextSecondary)
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        title.uppercase(),
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold,
        color = HToGoColors.TextSecondary,
        modifier = Modifier.padding(start = 22.dp, end = 18.dp, top = 18.dp, bottom = 8.dp)
    )
}

@Composable
private fun BadgeNegocio(modifier: Modifier = Modifier) {
    val (icon, label, color) = if (ES_DUENO) {
        Triple(Icons.Filled.Storefront, "Dueño del negocio", HToGoColors.Primary)
    } else {
        Triple(Icons.Filled.LocalShipping, "Repartidor de Aguas Del Valle", HToGoColors.AccentAmber)
    }
    Surface(
        shape = RoundedCornerShape(99.dp),
        color = color.copy(alpha = .12f),
        modifier = modifier
    ) {
        Row(
            Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = color, modifier = Modifier.size(14.dp))
            Spacer(Modifier.width(6.dp))
            Text(label, color = color, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun InfoCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.padding(horizontal = 18.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, HToGoColors.OutlineSoft),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(content = content)
    }
}

@Composable
private fun InfoRow(
    icon: ImageVector,
    label: String,
    valor: String,
    last: Boolean = false,
    onClick: () -> Unit
) {
    val readOnly = !ES_DUENO
    Row(
        Modifier
            .fillMaxWidth()
            .clickable(enabled = !readOnly) { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(if (readOnly) HToGoColors.OutlineSoft else HToGoColors.PrimarySoft),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon, null,
                tint = if (readOnly) HToGoColors.TextSecondary else HToGoColors.Primary,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(label, fontSize = 11.sp, color = HToGoColors.TextSecondary)
            Text(
                valor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                color = if (readOnly) HToGoColors.TextSecondary else HToGoColors.TextPrimary
            )
        }
        if (!readOnly) {
            Icon(Icons.Filled.SwapHoriz, null, tint = HToGoColors.Primary, modifier = Modifier.size(18.dp))
        }
    }
    if (!last) HorizontalDivider(color = HToGoColors.OutlineSoft)
}

@Composable
private fun VehiculoCard(v: VehiculoRepartidor, modifier: Modifier) {
    val borderColor = if (v.esPrincipal) HToGoColors.Primary.copy(alpha = .3f) else HToGoColors.OutlineSoft
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (v.esPrincipal) HToGoColors.Primary.copy(alpha = .04f) else Color.White
        ),
        border = BorderStroke(if (v.esPrincipal) 1.5.dp else 1.dp, borderColor),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(Modifier.padding(14.dp)) {
            if (v.esPrincipal) {
                Surface(
                    shape = RoundedCornerShape(99.dp),
                    color = HToGoColors.Primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Text(
                        "PRINCIPAL",
                        color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .border(1.dp, HToGoColors.PrimarySoft, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        if (v.tipo == "Pickup") Icons.Filled.LocalShipping else Icons.Filled.TwoWheeler,
                        null, tint = HToGoColors.Primary
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(v.tipo, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                    Text(
                        "${v.marca} ${v.modelo} ${v.anio} · ${v.capacidad} garrafones",
                        fontSize = 12.sp, color = HToGoColors.TextSecondary
                    )
                    Text(
                        v.placas,
                        fontSize = 11.sp, color = HToGoColors.Primary, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = HToGoColors.PrimarySoft
                ) {
                    Row(
                        Modifier
                            .clickable {}
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.SwapHoriz, null, tint = HToGoColors.Primary, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(
                            "Solicitar cambio",
                            color = HToGoColors.Primary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductoRow(p: ProductoPrecio, isLast: Boolean, onCambiar: () -> Unit) {
    Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(p.accent.copy(alpha = .12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.WaterDrop, null, tint = p.accent, modifier = Modifier.size(20.dp))
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text("${p.marca} · ${p.capacidad}", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Text(
                "$%.0f por garrafón".format(p.precio),
                fontSize = 12.sp, color = HToGoColors.TextSecondary
            )
        }
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = HToGoColors.PrimarySoft
        ) {
            Row(
                Modifier
                    .clickable { onCambiar() }
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Edit, null, tint = HToGoColors.Primary, modifier = Modifier.size(13.dp))
                Spacer(Modifier.width(4.dp))
                Text(
                    "Cambiar precio",
                    color = HToGoColors.Primary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
    if (!isLast) HorizontalDivider(color = HToGoColors.OutlineSoft)
}

@Composable
private fun SolicitudRow(s: SolicitudCambio, isLast: Boolean) {
    val (estadoTxt, estadoColor, estadoIcon) = when (s.estado) {
        EstadoSolicitud.PENDIENTE -> Triple("Pendiente", HToGoColors.AccentAmber, Icons.Filled.HourglassEmpty)
        EstadoSolicitud.APROBADA -> Triple("Aprobada", HToGoColors.AccentEmerald, Icons.Filled.CheckCircle)
        EstadoSolicitud.RECHAZADA -> Triple("Rechazada", HToGoColors.AccentRose, Icons.Filled.Cancel)
    }
    Column(Modifier.padding(14.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(HToGoColors.PrimarySoft),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Approval, null, tint = HToGoColors.Primary, modifier = Modifier.size(18.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(s.tipo, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                Text(s.fecha, fontSize = 11.sp, color = HToGoColors.TextSecondary)
            }
            Surface(
                shape = RoundedCornerShape(99.dp),
                color = estadoColor.copy(alpha = .14f)
            ) {
                Row(
                    Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(estadoIcon, null, tint = estadoColor, modifier = Modifier.size(12.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(estadoTxt, color = estadoColor, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
        if (s.comentario != null) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(HToGoColors.AccentRose.copy(alpha = .08f))
                    .padding(8.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(Icons.Filled.Info, null, tint = HToGoColors.AccentRose, modifier = Modifier.size(14.dp))
                Spacer(Modifier.width(6.dp))
                Text(s.comentario, fontSize = 11.sp, color = HToGoColors.TextPrimary)
            }
        }
    }
    if (!isLast) HorizontalDivider(color = HToGoColors.OutlineSoft)
}

@Composable
private fun HorarioCard(modifier: Modifier) {
    var auto by remember { mutableStateOf(true) }
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, HToGoColors.OutlineSoft),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(HToGoColors.AccentEmerald.copy(alpha = .12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Schedule, null, tint = HToGoColors.AccentEmerald, modifier = Modifier.size(18.dp))
                }
                Spacer(Modifier.width(10.dp))
                Column(Modifier.weight(1f)) {
                    Text("Atención automática activa", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Text(
                        "Te conectarás y desconectarás automáticamente",
                        fontSize = 11.sp, color = HToGoColors.TextSecondary
                    )
                }
                Switch(checked = auto, onCheckedChange = { auto = it })
            }
            Spacer(Modifier.height(14.dp))
            HorizontalDivider(color = HToGoColors.OutlineSoft)
            Spacer(Modifier.height(14.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                TimeInput("Hora de inicio", "8:00 AM", Modifier.weight(1f))
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward, null,
                    tint = HToGoColors.TextSecondary,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                TimeInput("Hora de fin", "7:00 PM", Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun TimeInput(label: String, valor: String, modifier: Modifier) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = HToGoColors.Background,
        border = BorderStroke(1.dp, HToGoColors.OutlineSoft),
        modifier = modifier
    ) {
        Column(Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
            Text(label.uppercase(), fontSize = 10.sp, color = HToGoColors.TextSecondary)
            Text(valor, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun QRCard(modifier: Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = HToGoColors.Primary),
        shape = RoundedCornerShape(14.dp)
    ) {
        Box(Modifier.background(Brush.linearGradient(listOf(HToGoColors.Primary, HToGoColors.PrimaryLight)))) {
            Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.QrCode2, null, tint = HToGoColors.Primary, modifier = Modifier.size(36.dp))
                }
                Spacer(Modifier.width(14.dp))
                Column(Modifier.weight(1f)) {
                    Text("Tu QR personal", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Text(
                        "Comparte tu link o QR con tus clientes para que pidan directo a tu purificadora.",
                        color = Color.White.copy(alpha = .8f), fontSize = 11.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                    Button(
                        onClick = {},
                        modifier = Modifier.padding(top = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = HToGoColors.Primary
                        )
                    ) {
                        Icon(Icons.Filled.Share, null, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Compartir", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun SwitchRow(
    icon: ImageVector,
    titulo: String,
    sub: String,
    checked: Boolean,
    isLast: Boolean = false,
    onChange: (Boolean) -> Unit
) {
    Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(HToGoColors.Background),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = HToGoColors.TextSecondary, modifier = Modifier.size(18.dp))
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(titulo, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Text(sub, fontSize = 11.sp, color = HToGoColors.TextSecondary)
        }
        Switch(
            checked = checked, onCheckedChange = onChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = HToGoColors.Primary
            )
        )
    }
    if (!isLast) HorizontalDivider(color = HToGoColors.OutlineSoft)
}

@Composable
private fun ActionRow(
    icon: ImageVector,
    titulo: String,
    redText: Boolean,
    isLast: Boolean = false,
    onClick: () -> Unit
) {
    val accent = HToGoColors.AccentRose
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(if (redText) accent.copy(alpha = .12f) else HToGoColors.Background),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon, null,
                tint = if (redText) accent else HToGoColors.TextSecondary,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(Modifier.width(12.dp))
        Text(
            titulo,
            fontSize = 14.sp, fontWeight = FontWeight.Medium,
            color = if (redText) accent else HToGoColors.TextPrimary,
            modifier = Modifier.weight(1f)
        )
        Icon(Icons.Filled.ChevronRight, null, tint = HToGoColors.TextTertiary)
    }
    if (!isLast) HorizontalDivider(color = HToGoColors.OutlineSoft)
}

@Preview(showBackground = true, widthDp = 412, heightDp = 868)
@Composable
fun PerfilRepartidorScreenPreview() {
    HToGoTheme { PerfilRepartidorScreen() }
}
