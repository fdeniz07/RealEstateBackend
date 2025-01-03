PGDMP     "                    {            real_estate_db    15.2    15.2 �    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    33554    real_estate_db    DATABASE     �   CREATE DATABASE real_estate_db WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Turkish_Turkey.1254';
    DROP DATABASE real_estate_db;
                db_user    false            �            1259    33556    advert_types    TABLE     �   CREATE TABLE public.advert_types (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    update_at timestamp(6) without time zone,
    title character varying(30) NOT NULL
);
     DROP TABLE public.advert_types;
       public         heap    db_user    false            �            1259    33555    advert_types_id_seq    SEQUENCE     |   CREATE SEQUENCE public.advert_types_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.advert_types_id_seq;
       public          db_user    false    215            �           0    0    advert_types_id_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.advert_types_id_seq OWNED BY public.advert_types.id;
          public          db_user    false    214            �            1259    33563    adverts    TABLE     �  CREATE TABLE public.adverts (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    update_at timestamp(6) without time zone,
    advert_status smallint,
    built_in boolean NOT NULL,
    "/desc/" character varying(300) NOT NULL,
    is_active boolean NOT NULL,
    location character varying(255),
    price double precision NOT NULL,
    slug character varying(200) NOT NULL,
    title character varying(150) NOT NULL,
    view_count integer,
    advert_type_id bigint NOT NULL,
    category_id bigint NOT NULL,
    city_id bigint NOT NULL,
    country_id bigint NOT NULL,
    district_id bigint NOT NULL,
    CONSTRAINT adverts_advert_status_check CHECK (((advert_status >= 0) AND (advert_status <= 2)))
);
    DROP TABLE public.adverts;
       public         heap    db_user    false            �            1259    33562    adverts_id_seq    SEQUENCE     w   CREATE SEQUENCE public.adverts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.adverts_id_seq;
       public          db_user    false    217            �           0    0    adverts_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.adverts_id_seq OWNED BY public.adverts.id;
          public          db_user    false    216            �            1259    33573 
   categories    TABLE     w  CREATE TABLE public.categories (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    update_at timestamp(6) without time zone,
    built_in boolean NOT NULL,
    icon character varying(50) NOT NULL,
    is_active boolean NOT NULL,
    seq integer NOT NULL,
    slug character varying(200) NOT NULL,
    title character varying(150) NOT NULL
);
    DROP TABLE public.categories;
       public         heap    db_user    false            �            1259    33572    categories_id_seq    SEQUENCE     z   CREATE SEQUENCE public.categories_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.categories_id_seq;
       public          db_user    false    219            �           0    0    categories_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.categories_id_seq OWNED BY public.categories.id;
          public          db_user    false    218            �            1259    33580    category_property_keys    TABLE     �   CREATE TABLE public.category_property_keys (
    id bigint NOT NULL,
    built_in boolean NOT NULL,
    name character varying(80) NOT NULL,
    category_id bigint NOT NULL
);
 *   DROP TABLE public.category_property_keys;
       public         heap    db_user    false            �            1259    33579    category_property_keys_id_seq    SEQUENCE     �   CREATE SEQUENCE public.category_property_keys_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 4   DROP SEQUENCE public.category_property_keys_id_seq;
       public          db_user    false    221            �           0    0    category_property_keys_id_seq    SEQUENCE OWNED BY     _   ALTER SEQUENCE public.category_property_keys_id_seq OWNED BY public.category_property_keys.id;
          public          db_user    false    220            �            1259    33587    category_property_value    TABLE     �   CREATE TABLE public.category_property_value (
    id bigint NOT NULL,
    value character varying(100) NOT NULL,
    advert_id bigint,
    category_property_key_id bigint
);
 +   DROP TABLE public.category_property_value;
       public         heap    db_user    false            �            1259    33586    category_property_value_id_seq    SEQUENCE     �   CREATE SEQUENCE public.category_property_value_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 5   DROP SEQUENCE public.category_property_value_id_seq;
       public          db_user    false    223            �           0    0    category_property_value_id_seq    SEQUENCE OWNED BY     a   ALTER SEQUENCE public.category_property_value_id_seq OWNED BY public.category_property_value.id;
          public          db_user    false    222            �            1259    33594    cities    TABLE     �   CREATE TABLE public.cities (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    update_at timestamp(6) without time zone,
    name character varying(50) NOT NULL,
    country_id bigint NOT NULL
);
    DROP TABLE public.cities;
       public         heap    db_user    false            �            1259    33593    cities_id_seq    SEQUENCE     v   CREATE SEQUENCE public.cities_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.cities_id_seq;
       public          db_user    false    225            �           0    0    cities_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.cities_id_seq OWNED BY public.cities.id;
          public          db_user    false    224            �            1259    33601    contacts    TABLE     R  CREATE TABLE public.contacts (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    update_at timestamp(6) without time zone,
    email character varying(60) NOT NULL,
    first_name character varying(30) NOT NULL,
    last_name character varying(30) NOT NULL,
    message character varying(300) NOT NULL
);
    DROP TABLE public.contacts;
       public         heap    db_user    false            �            1259    33600    contacts_id_seq    SEQUENCE     x   CREATE SEQUENCE public.contacts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.contacts_id_seq;
       public          db_user    false    227            �           0    0    contacts_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.contacts_id_seq OWNED BY public.contacts.id;
          public          db_user    false    226            �            1259    33608 	   countries    TABLE     �   CREATE TABLE public.countries (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    update_at timestamp(6) without time zone,
    name character varying(50) NOT NULL
);
    DROP TABLE public.countries;
       public         heap    db_user    false            �            1259    33607    countries_id_seq    SEQUENCE     y   CREATE SEQUENCE public.countries_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.countries_id_seq;
       public          db_user    false    229            �           0    0    countries_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.countries_id_seq OWNED BY public.countries.id;
          public          db_user    false    228            �            1259    33615 	   districts    TABLE     �   CREATE TABLE public.districts (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    update_at timestamp(6) without time zone,
    name character varying(50) NOT NULL,
    city_id bigint NOT NULL
);
    DROP TABLE public.districts;
       public         heap    db_user    false            �            1259    33614    districts_id_seq    SEQUENCE     y   CREATE SEQUENCE public.districts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.districts_id_seq;
       public          db_user    false    231            �           0    0    districts_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.districts_id_seq OWNED BY public.districts.id;
          public          db_user    false    230            �            1259    33622 	   favorites    TABLE     �   CREATE TABLE public.favorites (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    update_at timestamp(6) without time zone,
    advert_id bigint,
    user_id bigint
);
    DROP TABLE public.favorites;
       public         heap    db_user    false            �            1259    33621    favorites_id_seq    SEQUENCE     y   CREATE SEQUENCE public.favorites_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.favorites_id_seq;
       public          db_user    false    233            �           0    0    favorites_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.favorites_id_seq OWNED BY public.favorites.id;
          public          db_user    false    232            �            1259    33629    images    TABLE     V  CREATE TABLE public.images (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    update_at timestamp(6) without time zone,
    featured boolean NOT NULL,
    name character varying(255) NOT NULL,
    path character varying(255) NOT NULL,
    type character varying(5) NOT NULL,
    advert_id bigint NOT NULL
);
    DROP TABLE public.images;
       public         heap    db_user    false            �            1259    33628    images_id_seq    SEQUENCE     v   CREATE SEQUENCE public.images_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.images_id_seq;
       public          db_user    false    235            �           0    0    images_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.images_id_seq OWNED BY public.images.id;
          public          db_user    false    234            �            1259    33638    logs    TABLE     �  CREATE TABLE public.logs (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    update_at timestamp(6) without time zone,
    log_action character varying(255),
    advert_id bigint NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT logs_log_action_check CHECK (((log_action)::text = ANY ((ARRAY['CREATED'::character varying, 'UPDATED'::character varying, 'DELETED'::character varying, 'DECLINED'::character varying, 'TOUR_REQUEST_CREATED'::character varying, 'TOUR_REQUEST_ACCEPTED'::character varying, 'TOUR_REQUEST_DECLINED'::character varying, 'TOUR_REQUEST_CANCELED'::character varying])::text[])))
);
    DROP TABLE public.logs;
       public         heap    db_user    false            �            1259    33637    logs_id_seq    SEQUENCE     t   CREATE SEQUENCE public.logs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.logs_id_seq;
       public          db_user    false    237            �           0    0    logs_id_seq    SEQUENCE OWNED BY     ;   ALTER SEQUENCE public.logs_id_seq OWNED BY public.logs.id;
          public          db_user    false    236            �            1259    33646    roles    TABLE       CREATE TABLE public.roles (
    id bigint NOT NULL,
    role_name character varying(255) NOT NULL,
    CONSTRAINT roles_role_name_check CHECK (((role_name)::text = ANY ((ARRAY['ADMIN'::character varying, 'MANAGER'::character varying, 'CUSTOMER'::character varying])::text[])))
);
    DROP TABLE public.roles;
       public         heap    db_user    false            �            1259    33645    roles_id_seq    SEQUENCE     u   CREATE SEQUENCE public.roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.roles_id_seq;
       public          db_user    false    239            �           0    0    roles_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;
          public          db_user    false    238            �            1259    33654    tour_requests    TABLE     �  CREATE TABLE public.tour_requests (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    update_at timestamp(6) without time zone,
    status smallint,
    tour_date timestamp(6) without time zone,
    tour_time timestamp(6) without time zone,
    advert_id bigint NOT NULL,
    guest_user_id bigint NOT NULL,
    owner_user_id bigint NOT NULL,
    CONSTRAINT tour_requests_status_check CHECK (((status >= 0) AND (status <= 3)))
);
 !   DROP TABLE public.tour_requests;
       public         heap    db_user    false            �            1259    33653    tour_requests_id_seq    SEQUENCE     }   CREATE SEQUENCE public.tour_requests_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.tour_requests_id_seq;
       public          db_user    false    241            �           0    0    tour_requests_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.tour_requests_id_seq OWNED BY public.tour_requests.id;
          public          db_user    false    240            �            1259    33661 
   user_roles    TABLE     ]   CREATE TABLE public.user_roles (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);
    DROP TABLE public.user_roles;
       public         heap    db_user    false            �            1259    33667    users    TABLE       CREATE TABLE public.users (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    update_at timestamp(6) without time zone,
    built_in boolean NOT NULL,
    email character varying(80) NOT NULL,
    first_name character varying(30) NOT NULL,
    is_active boolean NOT NULL,
    last_name character varying(30) NOT NULL,
    password_hash character varying(255) NOT NULL,
    phone character varying(255) NOT NULL,
    reset_password_code character varying(255),
    user_name character varying(20) NOT NULL
);
    DROP TABLE public.users;
       public         heap    db_user    false            �            1259    33666    users_id_seq    SEQUENCE     u   CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public          db_user    false    244            �           0    0    users_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;
          public          db_user    false    243            �           2604    33559    advert_types id    DEFAULT     r   ALTER TABLE ONLY public.advert_types ALTER COLUMN id SET DEFAULT nextval('public.advert_types_id_seq'::regclass);
 >   ALTER TABLE public.advert_types ALTER COLUMN id DROP DEFAULT;
       public          db_user    false    215    214    215            �           2604    33566 
   adverts id    DEFAULT     h   ALTER TABLE ONLY public.adverts ALTER COLUMN id SET DEFAULT nextval('public.adverts_id_seq'::regclass);
 9   ALTER TABLE public.adverts ALTER COLUMN id DROP DEFAULT;
       public          db_user    false    216    217    217            �           2604    33576    categories id    DEFAULT     n   ALTER TABLE ONLY public.categories ALTER COLUMN id SET DEFAULT nextval('public.categories_id_seq'::regclass);
 <   ALTER TABLE public.categories ALTER COLUMN id DROP DEFAULT;
       public          db_user    false    218    219    219            �           2604    33583    category_property_keys id    DEFAULT     �   ALTER TABLE ONLY public.category_property_keys ALTER COLUMN id SET DEFAULT nextval('public.category_property_keys_id_seq'::regclass);
 H   ALTER TABLE public.category_property_keys ALTER COLUMN id DROP DEFAULT;
       public          db_user    false    221    220    221            �           2604    33590    category_property_value id    DEFAULT     �   ALTER TABLE ONLY public.category_property_value ALTER COLUMN id SET DEFAULT nextval('public.category_property_value_id_seq'::regclass);
 I   ALTER TABLE public.category_property_value ALTER COLUMN id DROP DEFAULT;
       public          db_user    false    222    223    223            �           2604    33597 	   cities id    DEFAULT     f   ALTER TABLE ONLY public.cities ALTER COLUMN id SET DEFAULT nextval('public.cities_id_seq'::regclass);
 8   ALTER TABLE public.cities ALTER COLUMN id DROP DEFAULT;
       public          db_user    false    225    224    225            �           2604    33604    contacts id    DEFAULT     j   ALTER TABLE ONLY public.contacts ALTER COLUMN id SET DEFAULT nextval('public.contacts_id_seq'::regclass);
 :   ALTER TABLE public.contacts ALTER COLUMN id DROP DEFAULT;
       public          db_user    false    227    226    227            �           2604    33611    countries id    DEFAULT     l   ALTER TABLE ONLY public.countries ALTER COLUMN id SET DEFAULT nextval('public.countries_id_seq'::regclass);
 ;   ALTER TABLE public.countries ALTER COLUMN id DROP DEFAULT;
       public          db_user    false    229    228    229            �           2604    33618    districts id    DEFAULT     l   ALTER TABLE ONLY public.districts ALTER COLUMN id SET DEFAULT nextval('public.districts_id_seq'::regclass);
 ;   ALTER TABLE public.districts ALTER COLUMN id DROP DEFAULT;
       public          db_user    false    230    231    231            �           2604    33625    favorites id    DEFAULT     l   ALTER TABLE ONLY public.favorites ALTER COLUMN id SET DEFAULT nextval('public.favorites_id_seq'::regclass);
 ;   ALTER TABLE public.favorites ALTER COLUMN id DROP DEFAULT;
       public          db_user    false    233    232    233            �           2604    33632 	   images id    DEFAULT     f   ALTER TABLE ONLY public.images ALTER COLUMN id SET DEFAULT nextval('public.images_id_seq'::regclass);
 8   ALTER TABLE public.images ALTER COLUMN id DROP DEFAULT;
       public          db_user    false    235    234    235            �           2604    33641    logs id    DEFAULT     b   ALTER TABLE ONLY public.logs ALTER COLUMN id SET DEFAULT nextval('public.logs_id_seq'::regclass);
 6   ALTER TABLE public.logs ALTER COLUMN id DROP DEFAULT;
       public          db_user    false    237    236    237            �           2604    33649    roles id    DEFAULT     d   ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);
 7   ALTER TABLE public.roles ALTER COLUMN id DROP DEFAULT;
       public          db_user    false    238    239    239            �           2604    33657    tour_requests id    DEFAULT     t   ALTER TABLE ONLY public.tour_requests ALTER COLUMN id SET DEFAULT nextval('public.tour_requests_id_seq'::regclass);
 ?   ALTER TABLE public.tour_requests ALTER COLUMN id DROP DEFAULT;
       public          db_user    false    240    241    241            �           2604    33670    users id    DEFAULT     d   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public          db_user    false    243    244    244            �          0    33556    advert_types 
   TABLE DATA           G   COPY public.advert_types (id, create_at, update_at, title) FROM stdin;
    public          db_user    false    215   Ǥ       �          0    33563    adverts 
   TABLE DATA           �   COPY public.adverts (id, create_at, update_at, advert_status, built_in, "/desc/", is_active, location, price, slug, title, view_count, advert_type_id, category_id, city_id, country_id, district_id) FROM stdin;
    public          db_user    false    217   �       �          0    33573 
   categories 
   TABLE DATA           k   COPY public.categories (id, create_at, update_at, built_in, icon, is_active, seq, slug, title) FROM stdin;
    public          db_user    false    219   �       �          0    33580    category_property_keys 
   TABLE DATA           Q   COPY public.category_property_keys (id, built_in, name, category_id) FROM stdin;
    public          db_user    false    221   �       �          0    33587    category_property_value 
   TABLE DATA           a   COPY public.category_property_value (id, value, advert_id, category_property_key_id) FROM stdin;
    public          db_user    false    223   ;�       �          0    33594    cities 
   TABLE DATA           L   COPY public.cities (id, create_at, update_at, name, country_id) FROM stdin;
    public          db_user    false    225   X�       �          0    33601    contacts 
   TABLE DATA           c   COPY public.contacts (id, create_at, update_at, email, first_name, last_name, message) FROM stdin;
    public          db_user    false    227   :�       �          0    33608 	   countries 
   TABLE DATA           C   COPY public.countries (id, create_at, update_at, name) FROM stdin;
    public          db_user    false    229   W�       �          0    33615 	   districts 
   TABLE DATA           L   COPY public.districts (id, create_at, update_at, name, city_id) FROM stdin;
    public          db_user    false    231   �       �          0    33622 	   favorites 
   TABLE DATA           Q   COPY public.favorites (id, create_at, update_at, advert_id, user_id) FROM stdin;
    public          db_user    false    233   n�       �          0    33629    images 
   TABLE DATA           a   COPY public.images (id, create_at, update_at, featured, name, path, type, advert_id) FROM stdin;
    public          db_user    false    235   ��       �          0    33638    logs 
   TABLE DATA           X   COPY public.logs (id, create_at, update_at, log_action, advert_id, user_id) FROM stdin;
    public          db_user    false    237   ��       �          0    33646    roles 
   TABLE DATA           .   COPY public.roles (id, role_name) FROM stdin;
    public          db_user    false    239   ��       �          0    33654    tour_requests 
   TABLE DATA           �   COPY public.tour_requests (id, create_at, update_at, status, tour_date, tour_time, advert_id, guest_user_id, owner_user_id) FROM stdin;
    public          db_user    false    241   ��       �          0    33661 
   user_roles 
   TABLE DATA           6   COPY public.user_roles (user_id, role_id) FROM stdin;
    public          db_user    false    242   ��       �          0    33667    users 
   TABLE DATA           �   COPY public.users (id, create_at, update_at, built_in, email, first_name, is_active, last_name, password_hash, phone, reset_password_code, user_name) FROM stdin;
    public          db_user    false    244   �       �           0    0    advert_types_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.advert_types_id_seq', 1, false);
          public          db_user    false    214            �           0    0    adverts_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.adverts_id_seq', 1, false);
          public          db_user    false    216            �           0    0    categories_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.categories_id_seq', 1, false);
          public          db_user    false    218            �           0    0    category_property_keys_id_seq    SEQUENCE SET     L   SELECT pg_catalog.setval('public.category_property_keys_id_seq', 1, false);
          public          db_user    false    220            �           0    0    category_property_value_id_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public.category_property_value_id_seq', 1, false);
          public          db_user    false    222            �           0    0    cities_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.cities_id_seq', 82, true);
          public          db_user    false    224            �           0    0    contacts_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.contacts_id_seq', 1, false);
          public          db_user    false    226            �           0    0    countries_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.countries_id_seq', 244, true);
          public          db_user    false    228            �           0    0    districts_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.districts_id_seq', 1947, true);
          public          db_user    false    230            �           0    0    favorites_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.favorites_id_seq', 1, false);
          public          db_user    false    232            �           0    0    images_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.images_id_seq', 1, false);
          public          db_user    false    234            �           0    0    logs_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.logs_id_seq', 1, false);
          public          db_user    false    236            �           0    0    roles_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.roles_id_seq', 1, false);
          public          db_user    false    238            �           0    0    tour_requests_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.tour_requests_id_seq', 1, false);
          public          db_user    false    240            �           0    0    users_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.users_id_seq', 1, false);
          public          db_user    false    243            �           2606    33561    advert_types advert_types_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.advert_types
    ADD CONSTRAINT advert_types_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.advert_types DROP CONSTRAINT advert_types_pkey;
       public            db_user    false    215            �           2606    33571    adverts adverts_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.adverts
    ADD CONSTRAINT adverts_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.adverts DROP CONSTRAINT adverts_pkey;
       public            db_user    false    217            �           2606    33578    categories categories_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.categories DROP CONSTRAINT categories_pkey;
       public            db_user    false    219            �           2606    33585 2   category_property_keys category_property_keys_pkey 
   CONSTRAINT     p   ALTER TABLE ONLY public.category_property_keys
    ADD CONSTRAINT category_property_keys_pkey PRIMARY KEY (id);
 \   ALTER TABLE ONLY public.category_property_keys DROP CONSTRAINT category_property_keys_pkey;
       public            db_user    false    221            �           2606    33592 4   category_property_value category_property_value_pkey 
   CONSTRAINT     r   ALTER TABLE ONLY public.category_property_value
    ADD CONSTRAINT category_property_value_pkey PRIMARY KEY (id);
 ^   ALTER TABLE ONLY public.category_property_value DROP CONSTRAINT category_property_value_pkey;
       public            db_user    false    223            �           2606    33599    cities cities_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.cities
    ADD CONSTRAINT cities_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.cities DROP CONSTRAINT cities_pkey;
       public            db_user    false    225            �           2606    33606    contacts contacts_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.contacts
    ADD CONSTRAINT contacts_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.contacts DROP CONSTRAINT contacts_pkey;
       public            db_user    false    227            �           2606    33613    countries countries_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.countries
    ADD CONSTRAINT countries_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.countries DROP CONSTRAINT countries_pkey;
       public            db_user    false    229            �           2606    33620    districts districts_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.districts
    ADD CONSTRAINT districts_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.districts DROP CONSTRAINT districts_pkey;
       public            db_user    false    231            �           2606    33627    favorites favorites_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.favorites
    ADD CONSTRAINT favorites_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.favorites DROP CONSTRAINT favorites_pkey;
       public            db_user    false    233            �           2606    33636    images images_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.images
    ADD CONSTRAINT images_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.images DROP CONSTRAINT images_pkey;
       public            db_user    false    235            �           2606    33644    logs logs_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.logs
    ADD CONSTRAINT logs_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.logs DROP CONSTRAINT logs_pkey;
       public            db_user    false    237            �           2606    33652    roles roles_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.roles DROP CONSTRAINT roles_pkey;
       public            db_user    false    239            �           2606    33660     tour_requests tour_requests_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.tour_requests
    ADD CONSTRAINT tour_requests_pkey PRIMARY KEY (id);
 J   ALTER TABLE ONLY public.tour_requests DROP CONSTRAINT tour_requests_pkey;
       public            db_user    false    241            �           2606    33676 "   users uk_6dotkott2kjsp8vw4d0m25fb7 
   CONSTRAINT     ^   ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);
 L   ALTER TABLE ONLY public.users DROP CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7;
       public            db_user    false    244            �           2606    33678 "   users uk_k8d0f2n7n88w1a16yhua64onx 
   CONSTRAINT     b   ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_k8d0f2n7n88w1a16yhua64onx UNIQUE (user_name);
 L   ALTER TABLE ONLY public.users DROP CONSTRAINT uk_k8d0f2n7n88w1a16yhua64onx;
       public            db_user    false    244            �           2606    33665    user_roles user_roles_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id);
 D   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT user_roles_pkey;
       public            db_user    false    242    242            �           2606    33674    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            db_user    false    244            �           2606    33694 #   adverts fk21qrq2u0401sn97wh6ah3ilk1    FK CONSTRAINT     �   ALTER TABLE ONLY public.adverts
    ADD CONSTRAINT fk21qrq2u0401sn97wh6ah3ilk1 FOREIGN KEY (country_id) REFERENCES public.countries(id);
 M   ALTER TABLE ONLY public.adverts DROP CONSTRAINT fk21qrq2u0401sn97wh6ah3ilk1;
       public          db_user    false    229    217    3281            �           2606    33699 #   adverts fk4sw6klp8cy4e8bbsa63fa1euo    FK CONSTRAINT     �   ALTER TABLE ONLY public.adverts
    ADD CONSTRAINT fk4sw6klp8cy4e8bbsa63fa1euo FOREIGN KEY (district_id) REFERENCES public.districts(id);
 M   ALTER TABLE ONLY public.adverts DROP CONSTRAINT fk4sw6klp8cy4e8bbsa63fa1euo;
       public          db_user    false    231    217    3283            �           2606    33704 2   category_property_keys fk5er4kwcmud2ca25pl73tm7bol    FK CONSTRAINT     �   ALTER TABLE ONLY public.category_property_keys
    ADD CONSTRAINT fk5er4kwcmud2ca25pl73tm7bol FOREIGN KEY (category_id) REFERENCES public.categories(id);
 \   ALTER TABLE ONLY public.category_property_keys DROP CONSTRAINT fk5er4kwcmud2ca25pl73tm7bol;
       public          db_user    false    221    3271    219            �           2606    33719 "   cities fk6gatmv9dwedve82icy8wrkdmk    FK CONSTRAINT     �   ALTER TABLE ONLY public.cities
    ADD CONSTRAINT fk6gatmv9dwedve82icy8wrkdmk FOREIGN KEY (country_id) REFERENCES public.countries(id);
 L   ALTER TABLE ONLY public.cities DROP CONSTRAINT fk6gatmv9dwedve82icy8wrkdmk;
       public          db_user    false    3281    229    225            �           2606    33729 %   favorites fk94xm4cw4wxorabgtc1drvd942    FK CONSTRAINT     �   ALTER TABLE ONLY public.favorites
    ADD CONSTRAINT fk94xm4cw4wxorabgtc1drvd942 FOREIGN KEY (advert_id) REFERENCES public.adverts(id);
 O   ALTER TABLE ONLY public.favorites DROP CONSTRAINT fk94xm4cw4wxorabgtc1drvd942;
       public          db_user    false    3269    233    217            �           2606    33714 3   category_property_value fk9tvwi4fg1r394h6ajxdk1ve2n    FK CONSTRAINT     �   ALTER TABLE ONLY public.category_property_value
    ADD CONSTRAINT fk9tvwi4fg1r394h6ajxdk1ve2n FOREIGN KEY (category_property_key_id) REFERENCES public.category_property_keys(id);
 ]   ALTER TABLE ONLY public.category_property_value DROP CONSTRAINT fk9tvwi4fg1r394h6ajxdk1ve2n;
       public          db_user    false    223    221    3273            �           2606    33679 #   adverts fkdld2ou7igo6cvnt4uyar0syec    FK CONSTRAINT     �   ALTER TABLE ONLY public.adverts
    ADD CONSTRAINT fkdld2ou7igo6cvnt4uyar0syec FOREIGN KEY (advert_type_id) REFERENCES public.advert_types(id);
 M   ALTER TABLE ONLY public.adverts DROP CONSTRAINT fkdld2ou7igo6cvnt4uyar0syec;
       public          db_user    false    3267    215    217            �           2606    33744     logs fkgc26df1sbiwekdpt87pe9cd1p    FK CONSTRAINT     �   ALTER TABLE ONLY public.logs
    ADD CONSTRAINT fkgc26df1sbiwekdpt87pe9cd1p FOREIGN KEY (advert_id) REFERENCES public.adverts(id);
 J   ALTER TABLE ONLY public.logs DROP CONSTRAINT fkgc26df1sbiwekdpt87pe9cd1p;
       public          db_user    false    217    3269    237            �           2606    33749     logs fkgqy8beil5y4almtq1tiyofije    FK CONSTRAINT        ALTER TABLE ONLY public.logs
    ADD CONSTRAINT fkgqy8beil5y4almtq1tiyofije FOREIGN KEY (user_id) REFERENCES public.users(id);
 J   ALTER TABLE ONLY public.logs DROP CONSTRAINT fkgqy8beil5y4almtq1tiyofije;
       public          db_user    false    244    237    3301            �           2606    33769 &   user_roles fkh8ciramu9cc9q3qcqiv4ue8a6    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id) REFERENCES public.roles(id);
 P   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6;
       public          db_user    false    239    3291    242            �           2606    33774 &   user_roles fkhfh9dx7w3ubf1co1vdev94g3f    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES public.users(id);
 P   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f;
       public          db_user    false    3301    242    244            �           2606    33734 %   favorites fkk7du8b8ewipawnnpg76d55fus    FK CONSTRAINT     �   ALTER TABLE ONLY public.favorites
    ADD CONSTRAINT fkk7du8b8ewipawnnpg76d55fus FOREIGN KEY (user_id) REFERENCES public.users(id);
 O   ALTER TABLE ONLY public.favorites DROP CONSTRAINT fkk7du8b8ewipawnnpg76d55fus;
       public          db_user    false    3301    244    233            �           2606    33709 3   category_property_value fkkwlai0moi2ax257gjvqa8keti    FK CONSTRAINT     �   ALTER TABLE ONLY public.category_property_value
    ADD CONSTRAINT fkkwlai0moi2ax257gjvqa8keti FOREIGN KEY (advert_id) REFERENCES public.adverts(id);
 ]   ALTER TABLE ONLY public.category_property_value DROP CONSTRAINT fkkwlai0moi2ax257gjvqa8keti;
       public          db_user    false    3269    223    217            �           2606    33684 #   adverts fkod1w7pipdnsngimrol2dce02r    FK CONSTRAINT     �   ALTER TABLE ONLY public.adverts
    ADD CONSTRAINT fkod1w7pipdnsngimrol2dce02r FOREIGN KEY (category_id) REFERENCES public.categories(id);
 M   ALTER TABLE ONLY public.adverts DROP CONSTRAINT fkod1w7pipdnsngimrol2dce02r;
       public          db_user    false    3271    217    219            �           2606    33764 )   tour_requests fkopgaqsmjfbp8hirs0nl2qkc53    FK CONSTRAINT     �   ALTER TABLE ONLY public.tour_requests
    ADD CONSTRAINT fkopgaqsmjfbp8hirs0nl2qkc53 FOREIGN KEY (owner_user_id) REFERENCES public.users(id);
 S   ALTER TABLE ONLY public.tour_requests DROP CONSTRAINT fkopgaqsmjfbp8hirs0nl2qkc53;
       public          db_user    false    241    244    3301            �           2606    33759 )   tour_requests fkpv1cgch7195ggapb90viskvf9    FK CONSTRAINT     �   ALTER TABLE ONLY public.tour_requests
    ADD CONSTRAINT fkpv1cgch7195ggapb90viskvf9 FOREIGN KEY (guest_user_id) REFERENCES public.users(id);
 S   ALTER TABLE ONLY public.tour_requests DROP CONSTRAINT fkpv1cgch7195ggapb90viskvf9;
       public          db_user    false    3301    241    244            �           2606    33739 "   images fks3sx3xkno0tegpdmypkrnmhdc    FK CONSTRAINT     �   ALTER TABLE ONLY public.images
    ADD CONSTRAINT fks3sx3xkno0tegpdmypkrnmhdc FOREIGN KEY (advert_id) REFERENCES public.adverts(id);
 L   ALTER TABLE ONLY public.images DROP CONSTRAINT fks3sx3xkno0tegpdmypkrnmhdc;
       public          db_user    false    3269    235    217            �           2606    33689 #   adverts fksy8s2nf2be3mpde0n025gwd6e    FK CONSTRAINT     �   ALTER TABLE ONLY public.adverts
    ADD CONSTRAINT fksy8s2nf2be3mpde0n025gwd6e FOREIGN KEY (city_id) REFERENCES public.cities(id);
 M   ALTER TABLE ONLY public.adverts DROP CONSTRAINT fksy8s2nf2be3mpde0n025gwd6e;
       public          db_user    false    225    3277    217            �           2606    33754 )   tour_requests fktn21jne9w8khd67oqvc2im2u0    FK CONSTRAINT     �   ALTER TABLE ONLY public.tour_requests
    ADD CONSTRAINT fktn21jne9w8khd67oqvc2im2u0 FOREIGN KEY (advert_id) REFERENCES public.adverts(id);
 S   ALTER TABLE ONLY public.tour_requests DROP CONSTRAINT fktn21jne9w8khd67oqvc2im2u0;
       public          db_user    false    217    3269    241            �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �      �   �  x���Kn�@���)�����ɮ0��i�,�r�`Vg��{M9�8���T�W/�N����pxQ��'�^�,M������ G;��>�d5PM���14B&�\'_}�)"kj�5C�3�=B�#^������|���CQ� Мlet��^x��u����Fpd�qw^�Y��:�;�έ�H_��^H�/�}чD��mE��[�\�Ɛ��#��T����\���"��v�<r�G�u�����H��'���TȢ�4V�$0����%r
���I��gV�Jݚu�O�ܪ[!�g�h�-�����߬�^�����r���}*)�2B^�Gy��5BZK�h	��O�(a�"��\��p�|r�v��>��'Wm�ى�ON���y4�Qԍ��!���Voi��F�c���bd��]�1�h����4�;$F"�r��h��&K�#���Y}����?�y͛�.�"FJ�-�#�u�NV�I�κ!$ȣӗ��3�N��z�46���D�P��R��%0A
[��Q� �|0p:��:�]#5A昽�ty��u��/(���Cr��u��1��)R�������O�l��ђ"�\�$�V�^�M����[*�=Nù��<TM�]-k(Cs�ޒ@28F�?28C���9�2x�R�|5Xyґ�#��l��3����\nQ �|a�|ːls	�/xr�/7��#܅�;��G$�i��� N��J]n�Iد��~���c      �      x������ � �      �   	  x���Ks۸�����2�JR&���l�K��%;uSٴ(DDD��G��s���n�ƮR�����8`�!��_��K6H��_�'�{_������>��^R�������z��4��1�T=ǅ�P������,�:�J��1y��!��c�[�R�	�EWB��Qe+!o�&M��-�$:�g�q�A��jH)�PYe�RX!�!��b�)���JUHAmm���� L�3)��Ƥ�A\m2F�L�e)�&c���,|c2��̖"��K+�1@��5#t�xmF�̆J�-ctΊ��!�d|鞀;�d|���"�چ�]�'����YTI1��d�]�2�*�֕f@Q׺�Hn�C��[��ـ�}ؘ%bm]r���H)�P��\�.���
Ә\J�̀B�x��3Fu(��/�V(m�6��y��P�6y�aa�>c8�M����"�"�%����ro�q^���3��Bf����c3j�!���aP���^bz�r�o���Y�^~z��ї��kզ}�r���Qa�E���ɬ�ɹ�|%�L�'ma�%����u(M��'��Z}@������͓wOB��W��)^-��f���ڼx�C���֕��9d������3�幯\�sz�h�(߭�C��"�(��2`�ͺ5���E���|��X]���!w\EW[�#w�T��]4��f�!�h�תd�.�\�oړ�c�*�N��1���}]KG������w��.�1z�A�ܚ#��2��1�;�ė��6:���n��_��V�Ť0�W2GM<fD��K�ǌ��a�B��
���s̨]:m�ە��^bcc���`-�3p��c�A�����+�[����)��섒�z������n�	�ӧ�
��}9sh�9���Fa�0BׂN֜0@�V�K����q��o�	w�e�L�M>>j9G������O����"��NL(��-�`ΐ^�]�JF�&�]��{�h�)�����y'�c8o�?ws]���X�c���!�	//�H�1�6acݦ�1p7M[BȸݴR�5U�a��;*��7Y�#R��VS�|�a�]�񺕭���IK��rk덾#t낛c�@����X�l��J��٫�q���H&�~��&,7�~��2�R�c�FҪ��R;ed�R{�5�5��o%#5r�ΟL���2���ȡEomݴ��2T#���RSj���?{��	��2�\0�Q��.��4�v��z)M�V!��
�QyFlS�D��.mR�҅{Bs�R�R�9�5��:�i,�)�,_�cJmi��;Z�R�d,�g�>��[Ɣ�'c����G���?.G�P�d���R�]2���?��C�ݟ2S;�3��Yi۠��7j��}�>5H�~�]}���$ctn�.�ܡнBJ��;D@�����kA��ȝ��.����N��1GY꒤fȝ}N�R��j����������z� ˨��ߝ[Z ��H��ND(:��@M��߾|q�0��¯[ӱ�ŵ���`賠�.��J+:�G&���S�L �Im�l�IRϒ�	^�R!�6�u�D�g�K�2�C�E���ՕG͐I�J�^��`E��ksqsKM���C��!�N\��>���֫Y��G�����)u>�m��vl����U_���q�F�h.�ª��]�!��qzw~
!�6W�ɵ-���R;��ߺ�m�M��}r�M�~�(��+3��z�P�l�W��SvoNw��GW��u0�I�{.}hR/�?�O��2�>I�!�L�'3_��3��ܭ�?��2��p�i��LR�ejkۭ �Lm��.�g�qA_������ u]��P��E�9�ꥬ�����.�d��I�@!��Ȩ�2�##�v��Q[e��\vצ(�MjJ9E��w&�k$��"��./�='��?!�|�KFR��)ިfBF��)�w�2)��$�\�:}�o�
�X����'�Z(2j�L��LqQ�L�]��?�)��v,�b};ʨ'3����$��y�L����if��횔��43��Zu>'�k����$�3ڳB^&���ʇ/#�:`Nݛ�_z��_Y��2��̰_��Q�f��t�m���A���n3���bX��#��qS��8*��P����j��"��!C����%���~�����E]�>.*�n��WE��n�-���W���tƼހ�=��ڇ�{l���6���y��3j =l��e�R�Q�(-�Q�E|�F	Ԩ��l�`�)��P�^K��d��F2��?�����ɨ5�C����}ۨ��:D?4���������JK 5�~���5�~�j.�gk~}5��2��      �      x���Kr�@����)�������5ɦ���<��G7�J�3���5U�J�Mv����P@wuvU���Ǜ��w���������������������7��ݤ&������?O����!u@�zy��?��߂��:�#�Ϗ6����fr��O�שO�Cj*�����a��S�q��}/ȓ�#/�A��]o��s��^�oH}uyI-`������Oݷ�P��v�G{�5p��������Ϗ��?ʏ�<tv!w�*^^�]s�����s��P����k�����Y�Ø@*����T:0�C�*���`�q�*�������T7�?n��~K�].P�ͱ9��]4q�7{�g��m���&uM�+���RW}ܟr�_����w���C-p�|6�59~\9�pܦ�-�A�0$����Vy�F�m>�8�?p+��j���k���[����*wڕ諸��n�p��N��&��tjF���ʭߧ��w�V��^������ʩ;��&���j���ln�����3�wʳ�d�6Q���X��S��@V���ۦ��^yԆ�T����{���=���-�7:��kV�|��5�a��iKnf�'�@�3�9�{�ˣM���u\���9��ʫ�4lb� `���6�7��+���i/o�i[�N`��l�pj/��卼�q<T����.��cG\9؞��!/`�\�W�A97�|m�U�M�Xa#��j���@�C76ǐT����{ň+z�T��g�ʇ�w�]8邵�f��*�M_�N���E�yx��G��|�X���W�	\Ʊ����G��inSǧ�X��������L�;��dĞ��3�|y���a��?^l>|Z(���j�K��4�!���r��ؤ���~)���~H��i��x��z!ʥ�������xy�X�ʽs�|Q}�����KN���m��yrX9�����-�}�`ed�[*�*�.��l��",��GjC@�r��|dϜ��$?�d�	�Q�z}�<l'�a�������..^����O�fB����'�]�*/�Gs��_�'�c_O����k�gj�i�G-��϶X-,S��/GJhȽ�`�mq��*{�H�gg#[��O)9X�Fm�$+GWFT��O��'+e�d��n����M�������r V���K����x
RN����|��V�S�<5d�����$�Ża���.U��⣒MM~��jh:$��n$�<S3Y�E[Kۿ���b�[�Q�\������+?Rl��zH9��>m�͎�尚�Ӵ4A��"��&1�r`�)�7*��ڦ"���V��ن��m��Χ��_C�rP���|O�ĥ+�"X(�",����sl�s�!��O7���|�ZZ9�V^��"mg�G�Ԁ6��.J�0K��N6�R>���J-�|3!x���zK���ss����$dS`��wZ0av�(!�g�s�VX����lA����o4�yXi�Y6L����z�=,#�doV�T��t��p��0�Z���\���a�)�/��������;�6t�>�ALJ!�ʣS3D����~��w�K� �[�W>=����y���Z愕G�gX�f�.X��yWux�~�-U�6`(ҢН�����|y�w��x)y0�Sm��	�|�
a���`� "@�ʫ���W5a ={y�|����+�~�~i ��]�M�[u���;���M�<ǥ T~����=�R:6ؼ��9��I�S�kȦ"�EIK��f��ɮ���zhl-B�`[�?ήב�[��H� \��X
A�4��EX�rL�y�)�.��h>h)�|���l�ԁK���հ�V^�t�y���-<q�rĝ,V즠�3��Y/C�APlQd�ְ�r��{c�3����m�u�gr3#�ZJgׄH�ݔ܌�t��t��HC������o
��^�H� �-�H��h���[oT�D3B�:�P�Y��c���&�[m]��`׳������ç
^�ե�m�ʫ6�v_W.}
�:�r*�M��IѨ�Ø���<:��w@Z��Ø��TG;�V{2����k^9�c*�ʷ�Eʹھ(�b���<�MHibt��\���'����I��"�` ��*��
�M�����*�mN�g@+��z&a9���9zz+x,��0��nS��I'��/��J^�mܪ�������Co���(^n�B;կ�z�K�6kL\y�j�=+�^߳vU�9���i�9O������|�[.J,
T���M�Ʀ���N=Wn��C�m����qV� ���r���3�[����"���R����2[҆A���"X��&a˄�ԒZ���E�(]�V�����4p�;�Ik��:��p-vϮ�|:ٵ�rXH�a[��-ks\��&�B~�x��RO������r*�s$/�/�NkOA#�7��z���&ВD��u�yb�Ά\�:O���z1������3��&�ZD�q�h���k���T��tJ�T�Z��ӌ����]\�J�(��霆`�S7X��/�:��j!��§��O=bhQ6\y��Ru�H=��b/�3�y(�K��%�ʫ(��۔rR��5��C�X�B]�c�zܦ�oP�D[��uTԜ�s(W�RJ�1{�HZ�YOqwRA�guFY���������D՞�n���բDH�S�^P�؋�o�ch�#��C�@���.`)&��继��|�;�ۺsTﱝ��򪅉�gZ��)G=��rhc�K̓|A�<���^�2Y��ְ����/]��>�So��[�Kɋx���;���ma��r�R��+�o	�y֕��	׶":B���������!׾4��1�@�4������e"`r�;*u��q����u
Y�+O�{p\�c�����wn�!آt@dʐ/-,忱���a(W�y�߮FV�����	�H��V�m���R,�A�!����\>?^:����1P��Yu������J����r�%���U��-�\N�;?�B�B�fa�5��r汙�Hy��U;O�[�C�'���DO�e����WN� ��GĕW�\=CYt�ǩX���C������Ư/HW�5'{$�-�Pxb1�������K��B�#L�X�,�ܽ�v��Q:�ӎ�r���8���4���(,*[-�Tu�(�wS�D0�r�� /2�:&ϟ����Q�ګ Y�O9z,�W~�!���q�N�0(���V�^�|�a�:���M=�̉K�����y)"Y�o���[h�ޗ�c�(��F���.J��Ӷ�W��R��l�G��D<zb"�R�� ��{�(��}O���0���+��鴮Q�;On/(&��_���t��:���pa.(a�N���1䍽��C��k'c˕��w����4(H{�=���Ѻj��:�`eu��mj�0#��.(������p�&X��zk�m�����i�'�B�a�K��~�]Ӣ�q���z�]��ya�n��u����T۴LZ�ô+e�`e���r��ѳ-�\6��
�u誊aX��.�l���eZ]~�U_�U��:)�+J����ɋ*�*'����=m�����-j��ɽHfK����IO�.Y�����(�D	��T�xA��#/7��3T�JiD�a���W��W�4�}��[��1��^)^jDX�J���}k���a�&�YVl�@�������vU�����6�ۻ�д+(��v�:#)5��K�)6`R�I;�p���w��ఌ���b�2-��;�pW���=S�%]��������{��Hg>�P��Kͥ��ۖ
�â�,�p�QN�����Yy��K�z��.��pA���9�!.󖘝l��lA;�
�>c�m.�:/���{R��6�\��L9��F��0��O,�٤#Y���G򌾽IE�st������U_OT�T��ޣ��A��l.X*ɗ7���k��*�m������A�JM�@�����k}�����\�6�P�
sᅋ��4)+:�bF�-�ؔ�^��d�u9������,���[ۯ"a�w{�v�$"ũ%ϣ�S����ޡ��@� �  ����:Y���`�\o��(=� ��i���0��6�z-rF�3�{�r>>���}ʉ�ci�:�ɦ�6�.Hn��G���r������wă���s(����%7�l�D��.=3�%%{�}q
�`��{�d�X�-4���Ζy`�.9.`)L�{Y�Jak\�����x�R��zZy�M��L�o||���k��6r�������_�'��-%��tY%�LM}/�֤�>�t7�ך���J��W4ۄ�r3)PMM�u�=�e�Z��}��@jU��z�I���kFI� ��-`���a���-����`�-Aϗ'.k�/o}��<�p��RC�~;-�N=�E�4(�X�ϱ�n��� �?�J�,2&���A��n�~:]��e�0x�\�7U��և,U�����>�
�
$�Wg�|�G)jԜ��5��U�O�߁��Ϗ�c��eme�x���7²B�L� �@
V�?�6`�Z���7�����Y)Q�W�𮯁�n\v��^-��(8!�ܸ����`u����	�F4��+�B�����i cal�׻�2�!E�
q�P��c%
�G��(q�q���&�܊� &��@f��)�M ����\���K�ɵ�5�rR=s|y�w]�X�a�O3���E��Zo�a�Z�t��\�ʭ޴k!к Ң=ߪ��nOr����k��n!�&
�i\��T����0����Y]�Bɜ�+w�S�{�`e�ձM�o��Ô3V�u�k]�F��)z��[�u�����y)4�V��.G��J ��(���Q$6:aƣ�.��p��%qN �线|��z�g�p�7��/h�����a�[<�mr^HK�ɻm捗a���̄e�q>4h�uRy¸����P>~,9X�y�˽|_���l�8���u����@o'��(���5�
h�{�y�V�M�y3�u���)�&[�S��g^�7�,?�iH���Lr����7k�����̓�Rmb-����7����eT�J�m\�T�v�4T��>��e�m����l��q/h1KZ�ֹ�OE�L��]�r%�u��@����~��h�EP
�p�Ο�&�ӿt��u��|��4�� `��߿ #⣟�������+MJ[��7ڧ�_�	�������g��#/?U�1cj��֙|�fM�%�g:^�ΑB����V�I����i�(/��,]����o�湊��@d�U<9�|Ig��W^Ф�/3i)2Փ�@��[JLhAY��Tl�\/F����?�Ԝ�^�~URtB��EY�%��-G�ym��4O���J�	�%���Sl}�N)��2�����Fv.��#:i�|���x�a {p�yT6s���K�R��]b/|a�*a�T(H���,�'.�	K���+��C\����?<������5@��Ã�:1����������2H�<�4�k�-�֟z�?ZN���#l�*y<���l��N{X�Y*O��&�O�h&D�XO�}�ן�M����u�h�=�h!����<:�W��������caP�Nl�ף�+���XH��3wyZ���בM)?Q��I�>M�z��$rs���.�>���f���~b����	��".�l�
�`తʑa!���Ob=n.�ʧnpby]�32*zrZΰ6�B��@��02º7�R1˓���^�����G�M#i�01�y��#x���j�8^a�ug�tv	la#����s��{��uk�g3������q��rA����mS1A����~�� ^�G��1��ގE��ץ����� �~:t�}��W��nXV���Ьw)����D{u�O�X��������eܔFϾ$�ܹ�#7����3i���O���:oQ����>�ȧm�&�/�I��^/Hj�����u[��{���*��l�O�ĴY[�]ǄS����a[\����2
N�M@�S�p�g�ڙMZ�y�=
s�+_�m�=+��-#�ǟN벬�������}��0(9�~/h!N���dV?�Bo�-Hz-e���u�R��u���t�aӪe>޺��W�����*����=Р��y�ʫ�����R���ƛ�;���c���kyiޤa��.P�ڴ^�ԕ��E��y�3�>�_�iR��6��릗o�$���p��yv-te{m��v����I^uH\*L��WWI��;�۬AX�Q����KJKm��yٺ+���a��ʫ^<�çRX�Ԫ���Jbo~�إ�$s���RS�u�
�Ҏ��_�AX��Sk�@�:������h��^�z�;^���e}�
43�܉�4���Z4��W�a��u%l�Mĥ����JU�C�k�n����^y))�6[�͠0���L�@n&Y)����bXcLXά��98��U�=k�AKE	UD�I�:3b>ǕH9��y��f�N6?yjO�������΍�m���qݮv���c������s]�K��ޗ�z-���>���դ8��{��B�ݰ�������j�\ي���S|�Z�ñYFZ.m�d}���VV��s�O�*nN/#��[��2�dF)$�yYY=�zuZ�D*I�@�U~l��z�RF,*]�D���\�l�@^�7���r�ٕ�~͞���V3㑸N�k�@���-u��O�@�Λ�S��r��@X����K�ʥh�v�^�t���ǀ����u���	�3�A#-�^{k�p��LW�z�S�J����q�RAz����-u%࢓R��-d꾗\2�S��h����y�ѯcIi �]��pu5,�6ȽoRȀ��Lw��RF�8�tкӥ�.o]к�j���N��>=/�}NM�2,:��Z7A�R	��<�\�W�J	��Bw������Y)#�HI6������;�D��Y���u４���%�˾���E�ʩ[[����-d��<_�6Xe�@�Gl�ن�����`����Z{�	(`�#�`Һ.�Ok,@����8��=�ty	���������2�~x�w�M��')�˶�R�p�Rxyi��aO���
=�>���q �L���Z���S3A �`\
C�?n�`t$,�
]q��3��>����N�?q�Z�����@�&[�8ZS�5a(]kԸ^�T��+�ɽ�j�vH���/���s���p�맳Z31(�)�߿���H��ݤ�&��	-v>��G�,++��sSM���&�L|���o^�,S�%�B9uA+4�EuqX���u^ŔYK������&9;J��6eBDŽ,�Hi�l�!�uUk��:t ��<o�|��ua��������O�"Md��ty��ە#�T��T��iPpfz��3���S��x�c��*��S��j�m���*�|l�W��+�`=���;�ѧ#-����Ⅻ���xÑ��jt"��X�c<�?���͔8�ّ6�)��������G�Dַ��=��{4�e�������u��a	�m�Ƽ����@�,F�`�m���#/�&$*�˂�]�QfJ\zԳ�mMZv9��t�:�����9��b���E�m��eɯ����}�����4N�7��>�0;>=9.�W�iߤ��Ɵ7z���C��b�Qi��Zܷ�D�.��<n����/���Қ�L\~��.�[܅E�׊.������w��4�9K��� \o��UX�Ea7�z�������%8ы���8����d�g�6�)!�r��_�}���-��*�D��ӥ�����Rk���>\�h��>J;�������lc������`���3_|�[�X}�����Ǐ��N      �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �     