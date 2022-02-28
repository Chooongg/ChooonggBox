package com.chooongg.core.adapter

import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.viewholder.BaseViewHolder

open class BindingHolder<BINDING : ViewBinding>(val binding: BINDING) : BaseViewHolder(binding.root)