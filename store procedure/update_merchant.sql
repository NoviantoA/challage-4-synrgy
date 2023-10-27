CREATE OR REPLACE FUNCTION public.update_merchant(p_merchant_id uuid, p_merchant_name character varying, p_merchant_location character varying, p_open boolean)
 RETURNS void
 LANGUAGE plpgsql
AS $function$
BEGIN
    UPDATE merchants
    SET
        merchant_name = p_merchant_name,
        merchant_location = p_merchant_location,
        open = p_open
    WHERE
        id = p_merchant_id;
END;
$function$
;
