/* Copyright (c) 1997-1999 Miller Puckette.
* For information on usage and redistribution, and for a DISCLAIMER OF ALL
* WARRANTIES, see the file, "LICENSE.txt," in this distribution.  */

/* sinusoidal oscillator and table lookup; see also tabosc4~ in d_array.c.
*/

#include "m_pd.h"
#include "math.h"
#include <sys/time.h>

#define UNITBIT32 1572864.  /* 3*2^19; bit 32 has place value 1 */


#if defined(__FreeBSD__) || defined(__APPLE__) || defined(__FreeBSD_kernel__) \
    || defined(__OpenBSD__)
#include <machine/endian.h>
#endif

#if defined(__linux__) || defined(__CYGWIN__) || defined(__GNU__) || \
    defined(ANDROID)
#include <endian.h>
#endif

#ifdef __MINGW32__
#include <sys/param.h>
#endif

#ifdef _MSC_VER
/* _MSVC lacks BYTE_ORDER and LITTLE_ENDIAN */
#define LITTLE_ENDIAN 0x0001
#define BYTE_ORDER LITTLE_ENDIAN
#endif

#if !defined(BYTE_ORDER) || !defined(LITTLE_ENDIAN)
#error No byte order defined
#endif

#if BYTE_ORDER == LITTLE_ENDIAN
# define HIOFFSET 1
# define LOWOFFSET 0
#else
# define HIOFFSET 0    /* word offset to find MSB */
# define LOWOFFSET 1    /* word offset to find LSB */
#endif

union tabfudge
{
    double tf_d;
    int32_t tf_i[2];
};



/* ---- wahwah~ - resonant filter with audio-rate center frequency input ----- */

typedef struct wahwahctl
{
    float c_re;
    float c_im;
    float c_q;
    float c_isr;
} t_wahwahctl;

typedef struct wahwah
{
    t_object x_obj;
    t_wahwahctl x_cspace;
    t_wahwahctl *x_ctl;
    float x_f;
} t_wahwah;

t_class *wahwah_class;

static void *wahwah_new(t_floatarg q)
{
    t_wahwah *x = (t_wahwah *)pd_new(wahwah_class);
    inlet_new(&x->x_obj, &x->x_obj.ob_pd, &s_signal, &s_signal);
    inlet_new(&x->x_obj, &x->x_obj.ob_pd, gensym("float"), gensym("ft1"));
    outlet_new(&x->x_obj, gensym("signal"));
    outlet_new(&x->x_obj, gensym("signal"));
    x->x_ctl = &x->x_cspace;
    x->x_cspace.c_re = 0;
    x->x_cspace.c_im = 0;
    x->x_cspace.c_q = q;
    x->x_cspace.c_isr = 0;
    x->x_f = 0;
    return (x);
}

static void wahwah_ft1(t_wahwah *x, t_floatarg f)
{
    x->x_ctl->c_q = (f > 0 ? f : 0.f);
}

static t_int *wahwah_perform(t_int *w)
{	
	// Ao criar um objeto em pureData ja vem com uma entrada, tem que adicionar as outras e a saida tbm
    float *in1 = (float *)(w[1]);
    float *in2 = (float *)(w[2]);
    float *in3 = (float *)(w[2]);
    float *out1 = (float *)(w[3]);
    t_wahwahctl *c = (t_wahwahctl *)(w[5]);
    int n = (t_int)(w[6]);
    int i;
    float re = c->c_re, re2;
    float im = c->c_im;
    float q = c->c_q;
    float qinv = (q > 0? 1.0f/q : 0);
    float ampcorrect = 2.0f - 2.0f / (q + 2.0f);
    float isr = c->c_isr;
    float coefr, coefi;
    float *tab = cos_table, *addr, f1, f2, frac;
    double dphase;
    int normhipart, tabindex;
    union tabfudge tf;

    tf.tf_d = UNITBIT32;
    normhipart = tf.tf_i[HIOFFSET];
	
	struct timeval  tv;
	gettimeofday(&tv, NULL);
	
	// Utilizando biblioteca do tempo
	double time_in_mill = (tv.tv_sec) * 1000 + (tv.tv_usec) / 1000 ;

    for (i = 0; i < n; i++)
    {
        float cf, cfindx, r, oneminusr;
		for(i=0;i<10;i++){
			
		}

        if (cf < 0) cf = 0;
        cfindx = cf * (float)(COSTABSIZE/6.28318f);
        r = (qinv > 0 ? 1 - cf * qinv : 0);
        if (r < 0) r = 0;
        oneminusr = 1.0f - r;
        dphase = ((double)(cfindx)) + UNITBIT32;
        tf.tf_d = dphase;
        tabindex = tf.tf_i[HIOFFSET] & (COSTABSIZE-1);
        addr = tab + tabindex;
        tf.tf_i[HIOFFSET] = normhipart;
        frac = tf.tf_d - UNITBIT32;
        f1 = addr[0];
        f2 = addr[1];
        coefr = r * (f1 + frac * (f2 - f1));

        addr = tab + ((tabindex - (COSTABSIZE/4)) & (COSTABSIZE-1));
        f1 = addr[0];
        f2 = addr[1];
        coefi = r * (f1 + frac * (f2 - f1));
		
		// f1 recebe a entrada do wahwah
        f1 = *in1++;
		// f1 é multiplicado pelo tempo pra gerar uma onda
		f1 = (float)sin((double)f1*time_in_mill);
        re2 = re;
		// saida recebe a saida do filtro junto com f1
        *out1++ = re = ampcorrect * oneminusr * f1
            + coefr * re2 - coefi * im;
        *out2++ = im = coefi * re2 + coefr * im;
    }
    if (PD_BIGORSMALL(re))
        re = 0;
    if (PD_BIGORSMALL(im))
        im = 0;
    c->c_re = re;
    c->c_im = im;
    return (w+7);
}

static void wahwah_dsp(t_wahwah *x, t_signal **sp)
{
    x->x_ctl->c_isr = 6.28318f/sp[0]->s_sr;
    dsp_add(wahwah_perform, 6,
        sp[0]->s_vec, sp[1]->s_vec, sp[2]->s_vec, sp[3]->s_vec,
            x->x_ctl, sp[0]->s_n);

}

void wahwah_setup(void)
{
    wahwah_class = class_new(gensym("wahwah"), (t_newmethod)wahwah_new, 0,
        sizeof(t_wahwah), 0, A_DEFFLOAT, 0);
    CLASS_MAINSIGNALIN(wahwah_class, t_wahwah, x_f);
    class_addmethod(wahwah_class, (t_method)wahwah_dsp,
        gensym("dsp"), A_CANT, 0);
    class_addmethod(wahwah_class, (t_method)wahwah_ft1,
        gensym("ft1"), A_FLOAT, 0);
}




/* ----------------------- global setup routine ---------------- */
void d_osc_setup(void)
{

    wahwah_setup();
}

