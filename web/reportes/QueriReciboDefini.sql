select a.cod_poligono,a.cod_sub_poligono,a.cod_residencia,
ifnull(concat(b.nombre_propietario,' ',b.apellido_propietario) ,'-') as propietario,
DATE_FORMAT(a.ultima_fecha_abonada,'%m-%Y') as mes_pago,DATE_FORMAT(a.ultima_fecha_abonada,'%d/%m/%Y'),
a.valor_recibo,a.saldo_actual,a.nuevo_saldo, c.desc_forma_pago,a.num_recibo_prov
from tbl_recibos_provi_defini a left outer join mst_propietarios b ON
a.cod_poligono = b.cod_poligono and a.cod_sub_poligono=b.cod_sub_poligono and a.cod_residencia=b.cod_residencia
left outer join mst_formas_pago c ON a.corr_forma_pago=c.corr_forma_pago
where a.estado_recibo='P' and a.num_recibo_prov>=1 and a.num_recibo_prov<=8