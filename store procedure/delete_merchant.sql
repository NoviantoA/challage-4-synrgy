CREATE OR REPLACE FUNCTION public.delete_merchant(p_merchant_id UUID)
RETURNS VOID
LANGUAGE plpgsql
AS $function$
BEGIN
    DELETE FROM merchants
    WHERE id = p_merchant_id;
END;
$function$;
;
