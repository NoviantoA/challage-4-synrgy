SELECT public.add_merchant('Josuka', 'Semarang', true);
 
SELECT public.update_merchant('fbf8eeec-f77e-442b-a219-48641fe3f87b', 'Josuka Update', 'Surabaya', true);

SELECT * FROM public.get_id_merchant('fbf8eeec-f77e-442b-a219-48641fe3f87b');

SELECT public.delete_merchant('fbf8eeec-f77e-442b-a219-48641fe3f87b');

SELECT * FROM public.get_all_merchant();

