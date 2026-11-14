-- Flyway V1 baseline schema (IMPL-010-1)
-- Source: Hibernate 6.1.5 / Spring Boot 3.0.0 entity model
-- Generated from isolated PostgreSQL 16 (schemagen container); not hand-edited DDL

﻿--
-- PostgreSQL database dump
--


-- Dumped from database version 16.16
-- Dumped by pg_dump version 16.16

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: advert_types; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.advert_types (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    is_deleted boolean,
    update_at timestamp(6) without time zone,
    title character varying(30) NOT NULL
);


--
-- Name: advert_types_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.advert_types_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: advert_types_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.advert_types_id_seq OWNED BY public.advert_types.id;


--
-- Name: adverts; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.adverts (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    is_deleted boolean,
    update_at timestamp(6) without time zone,
    advert_status smallint NOT NULL,
    built_in boolean NOT NULL,
    "desc" character varying(300),
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
    user_id bigint NOT NULL
);


--
-- Name: adverts_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.adverts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: adverts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.adverts_id_seq OWNED BY public.adverts.id;


--
-- Name: categories; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.categories (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    is_deleted boolean,
    update_at timestamp(6) without time zone,
    is_active boolean NOT NULL,
    built_in boolean NOT NULL,
    icon character varying(50) NOT NULL,
    seq integer NOT NULL,
    slug character varying(200) NOT NULL,
    title character varying(150) NOT NULL
);


--
-- Name: categories_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.categories_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: categories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.categories_id_seq OWNED BY public.categories.id;


--
-- Name: category_property_keys; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.category_property_keys (
    id bigint NOT NULL,
    built_in boolean NOT NULL,
    name character varying(80) NOT NULL,
    category_id bigint NOT NULL
);


--
-- Name: category_property_keys_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.category_property_keys_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: category_property_keys_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.category_property_keys_id_seq OWNED BY public.category_property_keys.id;


--
-- Name: category_property_value; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.category_property_value (
    id bigint NOT NULL,
    value character varying(100) NOT NULL,
    advert_id bigint,
    category_property_key_id bigint
);


--
-- Name: category_property_value_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.category_property_value_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: category_property_value_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.category_property_value_id_seq OWNED BY public.category_property_value.id;


--
-- Name: cities; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.cities (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    is_deleted boolean,
    update_at timestamp(6) without time zone,
    name character varying(50) NOT NULL,
    "plate number" integer NOT NULL,
    country_id bigint
);


--
-- Name: cities_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.cities_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: cities_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.cities_id_seq OWNED BY public.cities.id;


--
-- Name: contacts; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.contacts (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    is_deleted boolean,
    update_at timestamp(6) without time zone,
    email character varying(60) NOT NULL,
    first_name character varying(30) NOT NULL,
    last_name character varying(30) NOT NULL,
    message character varying(300) NOT NULL
);


--
-- Name: contacts_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.contacts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: contacts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.contacts_id_seq OWNED BY public.contacts.id;


--
-- Name: countries; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.countries (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    is_deleted boolean,
    update_at timestamp(6) without time zone,
    code character varying(3) NOT NULL,
    name character varying(50) NOT NULL
);


--
-- Name: countries_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.countries_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: countries_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.countries_id_seq OWNED BY public.countries.id;


--
-- Name: districts; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.districts (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    is_deleted boolean,
    update_at timestamp(6) without time zone,
    name character varying(50) NOT NULL,
    city_id bigint
);


--
-- Name: districts_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.districts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: districts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.districts_id_seq OWNED BY public.districts.id;


--
-- Name: favorites; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.favorites (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    is_deleted boolean,
    update_at timestamp(6) without time zone,
    advert_id bigint,
    user_id bigint
);


--
-- Name: favorites_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.favorites_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: favorites_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.favorites_id_seq OWNED BY public.favorites.id;


--
-- Name: files; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.files (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    is_deleted boolean,
    update_at timestamp(6) without time zone,
    featured boolean NOT NULL,
    name character varying(255) NOT NULL,
    path character varying(255) NOT NULL,
    type character varying(5) NOT NULL,
    file_id bigint
);


--
-- Name: files_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.files_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: files_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.files_id_seq OWNED BY public.files.id;


--
-- Name: images; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.images (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    is_deleted boolean,
    update_at timestamp(6) without time zone,
    featured boolean NOT NULL,
    name character varying(255) NOT NULL,
    path character varying(255) NOT NULL,
    type character varying(5) NOT NULL,
    advert_id bigint NOT NULL
);


--
-- Name: images_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.images_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: images_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.images_id_seq OWNED BY public.images.id;


--
-- Name: logs; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.logs (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    is_deleted boolean,
    update_at timestamp(6) without time zone,
    log_action character varying(255),
    advert_id bigint NOT NULL,
    user_id bigint NOT NULL
);


--
-- Name: logs_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.logs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: logs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.logs_id_seq OWNED BY public.logs.id;


--
-- Name: messages; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.messages (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    is_deleted boolean,
    update_at timestamp(6) without time zone,
    is_draft boolean,
    is_important boolean,
    is_read boolean,
    is_spam boolean,
    is_trash boolean,
    message_content character varying(255) NOT NULL,
    subject character varying(255) NOT NULL,
    receiver_user_id bigint NOT NULL,
    sender_user_id bigint NOT NULL
);


--
-- Name: messages_files; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.messages_files (
    message_id bigint NOT NULL,
    files_id bigint NOT NULL
);


--
-- Name: messages_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.messages_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: messages_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.messages_id_seq OWNED BY public.messages.id;


--
-- Name: roles; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.roles (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    is_deleted boolean,
    update_at timestamp(6) without time zone,
    description character varying(255),
    is_active boolean NOT NULL,
    role_name character varying(255) NOT NULL
);


--
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- Name: social_media; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.social_media (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    is_deleted boolean,
    update_at timestamp(6) without time zone,
    account_url character varying(255) NOT NULL,
    logo character varying(255) NOT NULL,
    platform_name character varying(255) NOT NULL,
    thumbnail character varying(255) NOT NULL,
    user_id bigint
);


--
-- Name: social_media_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.social_media_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: social_media_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.social_media_id_seq OWNED BY public.social_media.id;


--
-- Name: tour_requests; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tour_requests (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    is_deleted boolean,
    update_at timestamp(6) without time zone,
    status smallint,
    tour_date timestamp(6) without time zone,
    tour_time timestamp(6) without time zone,
    advert_id bigint NOT NULL,
    guest_user_id bigint NOT NULL,
    owner_user_id bigint NOT NULL
);


--
-- Name: tour_requests_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.tour_requests_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: tour_requests_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.tour_requests_id_seq OWNED BY public.tour_requests.id;


--
-- Name: user_roles; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.user_roles (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);


--
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    create_at timestamp(6) without time zone NOT NULL,
    is_deleted boolean,
    update_at timestamp(6) without time zone,
    activation_token character varying(255),
    is_active boolean,
    birth_date date,
    built_in boolean NOT NULL,
    email character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    gender character varying(255) NOT NULL,
    profile_image character varying(255),
    last_name character varying(255) NOT NULL,
    password_hash character varying(255) NOT NULL,
    phone character varying(255) NOT NULL,
    reset_password_code character varying(255),
    user_info character varying(255)
);


--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: users_messages; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.users_messages (
    user_id bigint NOT NULL,
    messages_id bigint NOT NULL
);


--
-- Name: advert_types id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.advert_types ALTER COLUMN id SET DEFAULT nextval('public.advert_types_id_seq'::regclass);


--
-- Name: adverts id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.adverts ALTER COLUMN id SET DEFAULT nextval('public.adverts_id_seq'::regclass);


--
-- Name: categories id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.categories ALTER COLUMN id SET DEFAULT nextval('public.categories_id_seq'::regclass);


--
-- Name: category_property_keys id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.category_property_keys ALTER COLUMN id SET DEFAULT nextval('public.category_property_keys_id_seq'::regclass);


--
-- Name: category_property_value id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.category_property_value ALTER COLUMN id SET DEFAULT nextval('public.category_property_value_id_seq'::regclass);


--
-- Name: cities id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cities ALTER COLUMN id SET DEFAULT nextval('public.cities_id_seq'::regclass);


--
-- Name: contacts id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.contacts ALTER COLUMN id SET DEFAULT nextval('public.contacts_id_seq'::regclass);


--
-- Name: countries id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.countries ALTER COLUMN id SET DEFAULT nextval('public.countries_id_seq'::regclass);


--
-- Name: districts id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.districts ALTER COLUMN id SET DEFAULT nextval('public.districts_id_seq'::regclass);


--
-- Name: favorites id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.favorites ALTER COLUMN id SET DEFAULT nextval('public.favorites_id_seq'::regclass);


--
-- Name: files id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.files ALTER COLUMN id SET DEFAULT nextval('public.files_id_seq'::regclass);


--
-- Name: images id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.images ALTER COLUMN id SET DEFAULT nextval('public.images_id_seq'::regclass);


--
-- Name: logs id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.logs ALTER COLUMN id SET DEFAULT nextval('public.logs_id_seq'::regclass);


--
-- Name: messages id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.messages ALTER COLUMN id SET DEFAULT nextval('public.messages_id_seq'::regclass);


--
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- Name: social_media id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.social_media ALTER COLUMN id SET DEFAULT nextval('public.social_media_id_seq'::regclass);


--
-- Name: tour_requests id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tour_requests ALTER COLUMN id SET DEFAULT nextval('public.tour_requests_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Name: advert_types advert_types_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.advert_types
    ADD CONSTRAINT advert_types_pkey PRIMARY KEY (id);


--
-- Name: adverts adverts_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.adverts
    ADD CONSTRAINT adverts_pkey PRIMARY KEY (id);


--
-- Name: categories categories_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- Name: category_property_keys category_property_keys_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.category_property_keys
    ADD CONSTRAINT category_property_keys_pkey PRIMARY KEY (id);


--
-- Name: category_property_value category_property_value_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.category_property_value
    ADD CONSTRAINT category_property_value_pkey PRIMARY KEY (id);


--
-- Name: cities cities_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cities
    ADD CONSTRAINT cities_pkey PRIMARY KEY (id);


--
-- Name: contacts contacts_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.contacts
    ADD CONSTRAINT contacts_pkey PRIMARY KEY (id);


--
-- Name: countries countries_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.countries
    ADD CONSTRAINT countries_pkey PRIMARY KEY (id);


--
-- Name: districts districts_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.districts
    ADD CONSTRAINT districts_pkey PRIMARY KEY (id);


--
-- Name: favorites favorites_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.favorites
    ADD CONSTRAINT favorites_pkey PRIMARY KEY (id);


--
-- Name: files files_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.files
    ADD CONSTRAINT files_pkey PRIMARY KEY (id);


--
-- Name: images images_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.images
    ADD CONSTRAINT images_pkey PRIMARY KEY (id);


--
-- Name: logs logs_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.logs
    ADD CONSTRAINT logs_pkey PRIMARY KEY (id);


--
-- Name: messages messages_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT messages_pkey PRIMARY KEY (id);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: social_media social_media_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.social_media
    ADD CONSTRAINT social_media_pkey PRIMARY KEY (id);


--
-- Name: tour_requests tour_requests_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tour_requests
    ADD CONSTRAINT tour_requests_pkey PRIMARY KEY (id);


--
-- Name: users uk_6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- Name: roles uk_716hgxp60ym1lifrdgp67xt5k; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT uk_716hgxp60ym1lifrdgp67xt5k UNIQUE (role_name);


--
-- Name: users_messages uk_9ybe7v51r6kklm7ecxyu5glp; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users_messages
    ADD CONSTRAINT uk_9ybe7v51r6kklm7ecxyu5glp UNIQUE (messages_id);


--
-- Name: users uk_du5v5sr43g5bfnji4vb8hg5s3; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_du5v5sr43g5bfnji4vb8hg5s3 UNIQUE (phone);


--
-- Name: messages_files uk_o7rprdg6yomcd2jmt17ws7sl0; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.messages_files
    ADD CONSTRAINT uk_o7rprdg6yomcd2jmt17ws7sl0 UNIQUE (files_id);


--
-- Name: user_roles user_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (role_id, user_id);


--
-- Name: users_messages users_messages_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users_messages
    ADD CONSTRAINT users_messages_pkey PRIMARY KEY (user_id, messages_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: adverts fk21qrq2u0401sn97wh6ah3ilk1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.adverts
    ADD CONSTRAINT fk21qrq2u0401sn97wh6ah3ilk1 FOREIGN KEY (country_id) REFERENCES public.countries(id);


--
-- Name: districts fk3g7x8w4lc7qxth7ibrr5j73mn; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.districts
    ADD CONSTRAINT fk3g7x8w4lc7qxth7ibrr5j73mn FOREIGN KEY (city_id) REFERENCES public.cities(id);


--
-- Name: users_messages fk3tpfu7mudlhpcrh6xufdkexha; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users_messages
    ADD CONSTRAINT fk3tpfu7mudlhpcrh6xufdkexha FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: adverts fk4sw6klp8cy4e8bbsa63fa1euo; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.adverts
    ADD CONSTRAINT fk4sw6klp8cy4e8bbsa63fa1euo FOREIGN KEY (district_id) REFERENCES public.districts(id);


--
-- Name: category_property_keys fk5er4kwcmud2ca25pl73tm7bol; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.category_property_keys
    ADD CONSTRAINT fk5er4kwcmud2ca25pl73tm7bol FOREIGN KEY (category_id) REFERENCES public.categories(id);


--
-- Name: cities fk6gatmv9dwedve82icy8wrkdmk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cities
    ADD CONSTRAINT fk6gatmv9dwedve82icy8wrkdmk FOREIGN KEY (country_id) REFERENCES public.countries(id);


--
-- Name: social_media fk8sj1fvdebqf2g7v1fmtolqxs3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.social_media
    ADD CONSTRAINT fk8sj1fvdebqf2g7v1fmtolqxs3 FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: favorites fk94xm4cw4wxorabgtc1drvd942; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.favorites
    ADD CONSTRAINT fk94xm4cw4wxorabgtc1drvd942 FOREIGN KEY (advert_id) REFERENCES public.adverts(id);


--
-- Name: messages_files fk9p7h4kcdphf4q6veplhjn1ug3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.messages_files
    ADD CONSTRAINT fk9p7h4kcdphf4q6veplhjn1ug3 FOREIGN KEY (message_id) REFERENCES public.messages(id);


--
-- Name: category_property_value fk9tvwi4fg1r394h6ajxdk1ve2n; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.category_property_value
    ADD CONSTRAINT fk9tvwi4fg1r394h6ajxdk1ve2n FOREIGN KEY (category_property_key_id) REFERENCES public.category_property_keys(id);


--
-- Name: adverts fkdld2ou7igo6cvnt4uyar0syec; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.adverts
    ADD CONSTRAINT fkdld2ou7igo6cvnt4uyar0syec FOREIGN KEY (advert_type_id) REFERENCES public.advert_types(id);


--
-- Name: adverts fkf7n5k10h0sannu3c0gn1rud1r; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.adverts
    ADD CONSTRAINT fkf7n5k10h0sannu3c0gn1rud1r FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: logs fkgc26df1sbiwekdpt87pe9cd1p; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.logs
    ADD CONSTRAINT fkgc26df1sbiwekdpt87pe9cd1p FOREIGN KEY (advert_id) REFERENCES public.adverts(id);


--
-- Name: logs fkgqy8beil5y4almtq1tiyofije; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.logs
    ADD CONSTRAINT fkgqy8beil5y4almtq1tiyofije FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: messages_files fkh4rgdfryxwhnwodulfu6c4x7p; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.messages_files
    ADD CONSTRAINT fkh4rgdfryxwhnwodulfu6c4x7p FOREIGN KEY (files_id) REFERENCES public.files(id);


--
-- Name: user_roles fkh8ciramu9cc9q3qcqiv4ue8a6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- Name: users_messages fkhf1luf5n405f3jtqtrbs2khfn; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users_messages
    ADD CONSTRAINT fkhf1luf5n405f3jtqtrbs2khfn FOREIGN KEY (messages_id) REFERENCES public.messages(id);


--
-- Name: user_roles fkhfh9dx7w3ubf1co1vdev94g3f; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: messages fkk4mpqp6gfuaelpcamqv01brkr; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT fkk4mpqp6gfuaelpcamqv01brkr FOREIGN KEY (sender_user_id) REFERENCES public.users(id);


--
-- Name: favorites fkk7du8b8ewipawnnpg76d55fus; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.favorites
    ADD CONSTRAINT fkk7du8b8ewipawnnpg76d55fus FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: category_property_value fkkwlai0moi2ax257gjvqa8keti; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.category_property_value
    ADD CONSTRAINT fkkwlai0moi2ax257gjvqa8keti FOREIGN KEY (advert_id) REFERENCES public.adverts(id);


--
-- Name: adverts fkod1w7pipdnsngimrol2dce02r; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.adverts
    ADD CONSTRAINT fkod1w7pipdnsngimrol2dce02r FOREIGN KEY (category_id) REFERENCES public.categories(id);


--
-- Name: files fkonirvxr35os9739900e1jp1v2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.files
    ADD CONSTRAINT fkonirvxr35os9739900e1jp1v2 FOREIGN KEY (file_id) REFERENCES public.messages(id);


--
-- Name: tour_requests fkopgaqsmjfbp8hirs0nl2qkc53; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tour_requests
    ADD CONSTRAINT fkopgaqsmjfbp8hirs0nl2qkc53 FOREIGN KEY (owner_user_id) REFERENCES public.users(id);


--
-- Name: tour_requests fkpv1cgch7195ggapb90viskvf9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tour_requests
    ADD CONSTRAINT fkpv1cgch7195ggapb90viskvf9 FOREIGN KEY (guest_user_id) REFERENCES public.users(id);


--
-- Name: messages fkrx9kj3k3dqvmcfk4my12a98c3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT fkrx9kj3k3dqvmcfk4my12a98c3 FOREIGN KEY (receiver_user_id) REFERENCES public.users(id);


--
-- Name: images fks3sx3xkno0tegpdmypkrnmhdc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.images
    ADD CONSTRAINT fks3sx3xkno0tegpdmypkrnmhdc FOREIGN KEY (advert_id) REFERENCES public.adverts(id);


--
-- Name: adverts fksy8s2nf2be3mpde0n025gwd6e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.adverts
    ADD CONSTRAINT fksy8s2nf2be3mpde0n025gwd6e FOREIGN KEY (city_id) REFERENCES public.cities(id);


--
-- Name: tour_requests fktn21jne9w8khd67oqvc2im2u0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tour_requests
    ADD CONSTRAINT fktn21jne9w8khd67oqvc2im2u0 FOREIGN KEY (advert_id) REFERENCES public.adverts(id);


--
-- PostgreSQL database dump complete
--


